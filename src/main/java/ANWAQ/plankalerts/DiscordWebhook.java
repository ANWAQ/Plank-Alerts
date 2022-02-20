package ANWAQ.plankalerts;


import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

class DiscordWebHook {

    public void SendWebhook(ByteArrayOutputStream screenshotOutput, String fileName, String discordUrl, int raidType, String planker, RoomIdentifier.Mobs NPC, OkHttpClient httpClient) throws IOException {

        String plankDesc = "**" + planker + "** just planked ";

        if (NPC != null) {
            plankDesc += "to ";
            switch (NPC) {
                case OLM:
                    plankDesc += "OLM ";
                    break;
                case TEKTON:
                    plankDesc += "TEKTON ";
                    break;
                case LITTLE_MUTTA:
                    plankDesc += "BABY MUTTA ";
                    break;
                case BIG_MUTTA:
                    plankDesc += "BIG MUTTA ";
                    break;
                case MISC_MUTTA:
                    plankDesc += "MUTTA ";
                    break;
                case SHAMANS:
                    plankDesc += "SHAMANS ";
                    break;
                case MYSTICS:
                    plankDesc += "MYSTICS ";
                    break;
                case TIGHTROPE:
                    plankDesc += "TIGHTROPE ";
                    break;
                case ICE_DEMOM:
                    plankDesc += "ICE DEMON ";
                    break;
                case GUARDIANS:
                    plankDesc += "GUARDIANS ";
                    break;
                case CRAB:
                    plankDesc += "CRABS ";
                    break;
                case VASA:
                    plankDesc += " VASA ";
                    break;
                case VANGUARDS:
                    plankDesc += " VANGUARDS ";
                    break;
                case VESPULA:
                    plankDesc += " VESPULA ";
                    break;
                case MAIDEN:
                    plankDesc += " MAIDEN ";
                    break;
                case BLOAT:
                    plankDesc += " BLOAT ";
                    break;
                case NYLO:
                    plankDesc += " NYLO ";
                    break;
                case SOTETSEG:
                    plankDesc += " SOTETSEG ";
                    break;
                case XARPUS:
                    plankDesc += " XARPUS ";
                    break;
                case VERZIK:
                    plankDesc += " VERZIK ";
                    break;
            }
        }

        if (raidType == 2) {
            plankDesc += "at COX!";
        } else {
            plankDesc += "at TOB!";
        }

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("payload_json", "{\"embeds\": [{\"title\": \"PLANK ALERT!\", \"description\": \"" + plankDesc + "\", \"color\": 95421, \"image\": {\"url\": \"attachment://" + URLEncoder.encode(fileName, "UTF-8") + ".png\"}}]}")
                .addFormDataPart("image", URLEncoder.encode(fileName + ".png", "UTF-8"),
                        RequestBody.create(MediaType.parse("image/*png"),
                                screenshotOutput.toByteArray()))
                .build();

        Request request = new Request.Builder()
                .url(discordUrl)
                .post(requestBody)
                .build();

        try {
            httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}