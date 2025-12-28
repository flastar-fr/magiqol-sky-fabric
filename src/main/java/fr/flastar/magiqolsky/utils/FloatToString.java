package fr.flastar.magiqolsky.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static fr.flastar.magiqolsky.utils.ClientLocaleUtils.getClientLocale;

public class FloatToString {
    public static String convertDecimalFloatToString(float f, int precision, char decimalSeparator, char groupingSeparator, boolean useGrouping) {
        if (Float.isNaN(f) || Float.isInfinite(f)) return String.valueOf(f);

        BigDecimal bd = BigDecimal.valueOf(f)
                .setScale(precision, RoundingMode.HALF_EVEN)
                .stripTrailingZeros();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(getClientLocale());
        symbols.setDecimalSeparator(decimalSeparator);
        symbols.setGroupingSeparator(groupingSeparator);

        DecimalFormat df = new DecimalFormat();
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingUsed(useGrouping);
        df.setGroupingSize(3);
        df.setMaximumFractionDigits(precision);
        df.setMinimumFractionDigits(0);

        return df.format(bd);
    }
}
