package ANWAQ.plankalerts;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("plankAlerts")
public interface PlankAlertsConfig extends Config {
    @ConfigItem(
            keyName = "saveLocally",
            name = "Save Screenshots Locally",
            description = "Save screenshots in local folder",
            position = 1
    )
    default boolean saveLocally() {
        return false;
    }

    @ConfigItem(
            keyName = "webhookEnabled",
            name = "Discord Webhook",
            description = "Allows you to send death photos automatically to a discord webhook. Read the github page for info.",
            position = 2
    )
    default boolean webhookEnabled() {
        return false;
    }

    @ConfigItem(
            position = 3,
            keyName = "webhookLink",
            name = "Webhook URL",
            description = "Put your webhook link here, the full thing copied from discord."
    )
    default String webhookLink() {
        return "";
    }
}