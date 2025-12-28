package fr.flastar.magiqolsky.containervalues;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ContainerValueData {
    private char decimalSeparator;
    private char groupingSeparator;
    private boolean enableGrouping;
    private boolean enableContainerValue;

    public ContainerValueData(Locale locale) {
        setDefaultsWithLocale(locale);
        this.enableGrouping = false;
        this.enableContainerValue = true;
    }

    public char decimalSeparator() {
        return decimalSeparator;
    }

    public char groupingSeparator() {
        return groupingSeparator;
    }

    public boolean enableGrouping() {
        return enableGrouping;
    }

    public boolean isContainerValueEnabled() {
        return enableContainerValue;
    }

    public void setDecimalSeparator(char newDecimalSeparator) {
        this.decimalSeparator = newDecimalSeparator;
    }

    public void setGroupingSeparator(char newGroupingSeparator) {
        this.groupingSeparator = newGroupingSeparator;
    }

    public void setEnableGrouping(boolean newEnableGrouping) {
        this.enableGrouping = newEnableGrouping;
    }

    public void setEnableContainerValue(boolean newEnableContainerValue) {
        this.enableContainerValue = newEnableContainerValue;
    }

    public void setDefaultsWithLocale(Locale locale) {
        setDefaultDecimalSeparatorWithLocale(locale);
        setDefaultGroupingSeparatorWithLocale(locale);
    }

    public void setDefaultDecimalSeparatorWithLocale(Locale locale) {
        this.decimalSeparator = new DecimalFormatSymbols(locale).getDecimalSeparator();
    }

    public void setDefaultGroupingSeparatorWithLocale(Locale locale) {
        this.groupingSeparator = new DecimalFormatSymbols(locale).getGroupingSeparator();
    }
}
