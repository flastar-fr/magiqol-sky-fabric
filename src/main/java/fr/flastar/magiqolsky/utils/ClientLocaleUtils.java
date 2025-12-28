package fr.flastar.magiqolsky.utils;

import net.minecraft.client.MinecraftClient;

import java.util.Locale;

public class ClientLocaleUtils {
    public static Locale getClientLocale() {
        MinecraftClient client = MinecraftClient.getInstance();

        String localeCode = client.getLanguageManager().getLanguage();

        String languageTag = localeCode.replace('_', '-');

        return Locale.forLanguageTag(languageTag);
    }
}
