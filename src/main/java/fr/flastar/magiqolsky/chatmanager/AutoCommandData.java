package fr.flastar.magiqolsky.chatmanager;

public class AutoCommandData {
    private boolean isAutoFlyingEnabled;

    public AutoCommandData() {
        this.isAutoFlyingEnabled = true;
    }

    public boolean isAutoFlyingEnabled() {
        return isAutoFlyingEnabled;
    }

    public void changeIsAutoFlyingEnabled(boolean newValue) {
        isAutoFlyingEnabled = newValue;
    }
}
