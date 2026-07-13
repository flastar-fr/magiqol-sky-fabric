package fr.flastar.magiqolsky.utils;

import net.minecraft.client.Minecraft;

import java.util.Locale;

public class ClientLocaleUtils {
    public static Locale getClientLocale() {
        Minecraft client = Minecraft.getInstance();

        String localeCode = client.getLanguageManager().getSelected();

        String languageTag = localeCode.replace('_', '-');

        return Locale.forLanguageTag(languageTag);
    }
}