package fr.flastar.magiqolsky.chatmanager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManagerData {
    private boolean isAutoFlyingEnabled;
    private boolean isBetterBienvenueEnabled;
    private boolean isTextReplacementEnabled;
    private boolean isAutoNightVisionEnabled;
    private boolean isMessageHourEnabled;

    private String messageHourFormat;

    private final List<TextReplacerEntry> textReplacers;

    private final transient Map<String, String> textReplacersCache = new HashMap<>();

    public ChatManagerData(List<TextReplacerEntry> textReplacers) {
        this.isAutoFlyingEnabled = false;
        this.isBetterBienvenueEnabled = true;
        this.isTextReplacementEnabled = true;
        this.isAutoNightVisionEnabled = false;
        this.isMessageHourEnabled = true;
        this.messageHourFormat = "'['HH:mm:ss']'";

        this.textReplacers = textReplacers;
    }

    public ChatManagerData() {
        this(new ArrayList<>());
    }

    public boolean isAutoFlyingEnabled() {
        return isAutoFlyingEnabled;
    }

    public void changeIsAutoFlyingEnabled(boolean newValue) {
        isAutoFlyingEnabled = newValue;
    }

    public boolean isBetterBienvenueEnabled() {
        return isBetterBienvenueEnabled;
    }

    public void changeIsBetterBienvenueEnabled(boolean newValue) {
        isBetterBienvenueEnabled = newValue;
    }

    public boolean isTextReplacementEnabled() {
        return isTextReplacementEnabled;
    }

    public void changeIsTextReplacementEnabled(boolean newValue) {
        isTextReplacementEnabled = newValue;
    }

    public boolean isAutoNightVisionEnabled() {
        return isAutoNightVisionEnabled;
    }

    public void changeIsAutoNightVisionEnabled(boolean newValue) {
        isAutoNightVisionEnabled = newValue;
    }

    public boolean isMessageHourEnabled() {
        return isMessageHourEnabled;
    }

    public void changeIsMessageHourEnabled(boolean newValue) {
        isMessageHourEnabled = newValue;
    }

    public String messageHourFormat() {
        return messageHourFormat;
    }

    public void changeMessageHourFormat(String newValue) {
        messageHourFormat = newValue;
    }

    public List<TextReplacerEntry> textReplacers() {
        return textReplacers;
    }

    public void rebuildCache() {
        textReplacersCache.clear();
        for (TextReplacerEntry entry : textReplacers) {
            if (entry.key != null && !entry.key.trim().isEmpty()) {
                textReplacersCache.put(entry.key, entry.value);
            }
        }
    }

    public String applyReplacements(String message) {
        if (textReplacersCache.isEmpty()) rebuildCache();

        for (Map.Entry<String, String> entry : textReplacersCache.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message;
    }

    public void makeSavable() {
        textReplacers.removeIf(e -> e.key == null || e.key.trim().isEmpty() || e.value.trim().isEmpty());
        rebuildCache();
    }
}
