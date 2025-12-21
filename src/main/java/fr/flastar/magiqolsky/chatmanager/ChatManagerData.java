package fr.flastar.magiqolsky.chatmanager;

import java.util.HashMap;
import java.util.Map;

public class ChatManagerData {
    private boolean isAutoFlyingEnabled;
    private boolean isBetterBienvenueEnabled;
    private final Map<String, String> textReplacers;

    public ChatManagerData() {
        this.isAutoFlyingEnabled = false;
        this.isBetterBienvenueEnabled = true;
        this.textReplacers = new HashMap<>();
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

    public Map<String, String> textReplacers() {
        return textReplacers;
    }

    public void addNewTextReplacer(String keyToSeek, String valueToReplaceWith) {
        this.textReplacers.put(keyToSeek, valueToReplaceWith);
    }
}
