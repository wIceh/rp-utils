package me.yesice.rputils.utils;

import java.util.concurrent.TimeUnit;

public class Util {

    public static String formatTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        StringBuilder stringBuilder = new StringBuilder();
        if (day > 0) {
            stringBuilder.append(day).append("d ");
        }
        if (hours > 0) {
            stringBuilder.append(hours).append("h ");
        }
        if (minutes > 0) {
            stringBuilder.append(minutes).append("m ");
        }
        stringBuilder.append(second).append("s");


        return stringBuilder.toString();
    }

    public static String toSmallText(String text) {
        text = text.replace("a", "ᴀ");
        text = text.replace("b", "ʙ");
        text = text.replace("c", "ᴄ");
        text = text.replace("d", "ᴅ");
        text = text.replace("e", "ᴇ");
        text = text.replace("f", "ꜰ");
        text = text.replace("g", "ɢ");
        text = text.replace("h", "ʜ");
        text = text.replace("i", "ɪ");
        text = text.replace("j", "ᴊ");
        text = text.replace("k", "ᴋ");
        text = text.replace("l", "ʟ");
        text = text.replace("m", "ᴍ");
        text = text.replace("n", "ɴ");
        text = text.replace("o", "ᴏ");
        text = text.replace("p", "ᴘ");
        text = text.replace("q", "ǫ");
        text = text.replace("r", "ʀ");
        text = text.replace("s", "ꜱ");
        text = text.replace("t", "ᴛ");
        text = text.replace("u", "ᴜ");
        text = text.replace("v", "ᴠ");
        text = text.replace("w", "ᴡ");
        text = text.replace("x", "х");
        text = text.replace("y", "ʏ");
        text = text.replace("z", "ᴢ");
        text = text.replace("A", "ᴀ");
        text = text.replace("B", "ʙ");
        text = text.replace("C", "ᴄ");
        text = text.replace("D", "ᴅ");
        text = text.replace("E", "ᴇ");
        text = text.replace("F", "ꜰ");
        text = text.replace("G", "ɢ");
        text = text.replace("H", "ʜ");
        text = text.replace("I", "ɪ");
        text = text.replace("J", "ᴊ");
        text = text.replace("K", "ᴋ");
        text = text.replace("L", "ʟ");
        text = text.replace("M", "ᴍ");
        text = text.replace("N", "ɴ");
        text = text.replace("O", "ᴏ");
        text = text.replace("P", "ᴘ");
        text = text.replace("Q", "ǫ");
        text = text.replace("R", "ʀ");
        text = text.replace("S", "ꜱ");
        text = text.replace("T", "ᴛ");
        text = text.replace("U", "ᴜ");
        text = text.replace("V", "ᴠ");
        text = text.replace("W", "ᴡ");
        text = text.replace("X", "x");
        text = text.replace("Y", "ʏ");
        text = text.replace("Z", "ᴢ");

        return text;
    }
}
