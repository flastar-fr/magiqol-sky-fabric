package fr.flastar.magiqolsky.utils;

import net.minecraft.client.MinecraftClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FloatToString {

    public static String format(float value, int precision) {
        Locale locale = getClientLocale();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        DecimalFormat format = new DecimalFormat(buildPattern(precision), symbols);

        format.setGroupingUsed(false);
        return format.format(value);
    }

    private static String buildPattern(int precision) {
        return precision <= 0
                ? "0"
                : "0." + "#".repeat(precision);
    }

    private static Locale getClientLocale() {
        String lang = MinecraftClient.getInstance()
                .getLanguageManager()
                .getLanguage();

        return Locale.forLanguageTag(lang.replace('_', '-'));
    }
}
