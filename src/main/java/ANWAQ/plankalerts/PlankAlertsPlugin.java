package ANWAQ.plankalerts;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Varbits;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.DrawManager;
import net.runelite.client.util.ImageCapture;
import net.runelite.client.util.ImageUploadStyle;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

import static ANWAQ.plankalerts.RoomIdentifier.findRoom;

@Slf4j
@PluginDescriptor(
        name = "Plank Alerts",
        description = "Takes screenshots when a death occurs in a raid and optionally posts it in a discord channel.",
        tags = {"raids", "cox", "tob", "plank", "death"}
)
public class PlankAlertsPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private ImageCapture imageCapture;

    @Inject
    private DrawManager drawManager;

    @Inject
    private ScheduledExecutorService executor;

    @Inject
    private PlankAlertsConfig config;

    @Getter
    private boolean inTob;

    @Getter
    private boolean inCOX;

    @Provides
    PlankAlertsConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PlankAlertsConfig.class);
    }

    HashMap<String, Long> planks = new HashMap<>();

    @Subscribe
    public void onActorDeath(ActorDeath actorDeath) {
        if (!inTob && !inCOX) return;
        Actor actor = actorDeath.getActor();
        if (actor instanceof Player) {
            Player player = (Player) actor;
            String name = player.getName();

            /*
                A map of deaths is kept to prevent duplicate entries when multiple
                hit splats occur on the same tick, triggering multiple onActorDeath events.
             */

            long currentTime = System.currentTimeMillis();
            if (planks.containsKey(name)) {
                long lastDeath = planks.get(name);
                if (lastDeath > currentTime - 5000) {
                    planks.put(name, currentTime);
                    return;
                }
            }

            planks.put(name, currentTime);
            RoomIdentifier.Mobs findNPC = findRoom(client.getNpcs());

            String noSpacesName = name.replaceAll("\\s", "");

            if (inTob) {
                plankAlert(noSpacesName + "_PLANK_TOB", "Plank Alerts", 1, name, findNPC);
            } else if (inCOX) {
                plankAlert(noSpacesName + "_PLANK_COX", "Plank Alerts", 2, name, findNPC);
            }
        }
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event) {
        inTob = client.getVar(Varbits.THEATRE_OF_BLOOD) > 1;
        inCOX = client.getVar(Varbits.IN_RAID) > 0;
    }

    private void plankAlert(String fileName, String subDir, int raidType, String planker, RoomIdentifier.Mobs NPC) {
        Consumer<Image> imageCallback = (img) ->
        {
            executor.submit(() -> {
                try {
                    takeScreenshot(fileName, subDir, img, raidType, planker, NPC);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        };
        drawManager.requestNextFrameListener(imageCallback);
    }

    private void takeScreenshot(String fileName, String subDir, Image image, int raidType, String planker, RoomIdentifier.Mobs NPC) throws IOException {
        BufferedImage screenshot = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = screenshot.getGraphics();
        int gameOffsetX = 0;
        int gameOffsetY = 0;
        graphics.drawImage(image, gameOffsetX, gameOffsetY, null);
        if (config.saveLocally())
            imageCapture.takeScreenshot(screenshot, fileName, subDir, false, ImageUploadStyle.NEITHER);
        ByteArrayOutputStream screenshotOutput = new ByteArrayOutputStream();
        ImageIO.write(screenshot, "png", screenshotOutput);

        if (config.webhookEnabled() && !config.webhookLink().equals("")) {
            new DiscordWebHook().SendWebhook(screenshotOutput, fileName, config.webhookLink(), raidType, planker, NPC);
        }
    }
}
