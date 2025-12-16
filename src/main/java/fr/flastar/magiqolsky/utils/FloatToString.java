package fr.flastar.magiqolsky.utils;

import net.minecraft.client.MinecraftClient;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FloatToString {
    public static String convertDecimalFloatToString(float f, int precision) {
        Locale clientLocale = getClientLocale();
        String formatString = "%." + precision + "f";

        String formattedString = String.format(clientLocale, formatString, f);
        char decimalSeparator = new DecimalFormatSymbols(clientLocale).getDecimalSeparator();

        return removeDecimals(formattedString, decimalSeparator);
    }

    public static String removeDecimals(String number, char decimalSeparator) {
        String decimalSeparatorSequence = Character.toString(decimalSeparator);

        boolean containsDecimal = number.contains(decimalSeparatorSequence);
        boolean endWith0OrDot = number.endsWith("0") || number.endsWith(decimalSeparatorSequence);

        while (endWith0OrDot && number.length() > 1 && containsDecimal) {
            number = number.substring(0, number.length() - 1);
            containsDecimal = number.contains(decimalSeparatorSequence);
            endWith0OrDot = number.endsWith("0") || number.endsWith(decimalSeparatorSequence);
        }

        return number;
    }

    public static Locale getClientLocale() {
        MinecraftClient client = MinecraftClient.getInstance();

        String localeCode = client.getLanguageManager().getLanguage();

        String languageTag = localeCode.replace('_', '-');

        return Locale.forLanguageTag(languageTag);
    }
}
