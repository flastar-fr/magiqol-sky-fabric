package fr.flastar.magiqolsky.chatmanager;

public class ChatManagerData {
    private boolean isAutoFlyingEnabled;
    private boolean isBetterBienvenueEnabled;

    public ChatManagerData() {
        this.isAutoFlyingEnabled = false;
        this.isBetterBienvenueEnabled = true;
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
}
