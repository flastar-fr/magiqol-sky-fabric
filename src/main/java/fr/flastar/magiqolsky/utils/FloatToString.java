package fr.flastar.magiqolsky.utils;

public class FloatToString {

    public static String convertDecimalFloatToString(float f, int precision) {
        String formatString = "%." + precision + "f";

        return String.format(formatString, f);
    }
}
