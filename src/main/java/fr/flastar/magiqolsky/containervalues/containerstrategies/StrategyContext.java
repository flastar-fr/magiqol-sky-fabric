package fr.flastar.magiqolsky.containervalues.containerstrategies;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class StrategyContext {
    private final ScreenHandler handler;
    private final Text title;
    private final @Nullable PlayerInventory playerInventory;
    private int backgroundHeight;

    public StrategyContext(ScreenHandler handler, Text title, @Nullable PlayerInventory playerInventory, int backgroundHeight) {
        this.handler = handler;
        this.title = title;
        this.playerInventory = playerInventory;
        this.backgroundHeight = backgroundHeight;
    }

    public ScreenHandler handler() {
        return handler;
    }

    public Text title() {
        return title;
    }

    public @Nullable PlayerInventory playerInventory() {
        return playerInventory;
    }

    public int backgroundHeight() {
        return backgroundHeight;
    }

    public void updateBackgroundHeight(int backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }
}