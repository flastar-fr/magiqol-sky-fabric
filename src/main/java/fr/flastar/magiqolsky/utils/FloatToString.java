package fr.flastar.magiqolsky.utils;

public class FloatToString {

    public static String convertDecimalFloatToString(float f, int precision) {
        String formatString = "%." + precision + "f";

        String formattedString = String.format(formatString, f);

        return removeDecimals(formattedString);
    }

    public static String removeDecimals(String number) {
        boolean containsDecimal = number.contains(",");
        boolean endWith0OrDot = number.endsWith("0") || number.endsWith(",");

        while (endWith0OrDot && number.length() > 1 && containsDecimal) {
            number = number.substring(0, number.length() - 1);
            containsDecimal = number.contains(",");
            endWith0OrDot = number.endsWith("0") || number.endsWith(",");
        }

        return number;
    }
}
