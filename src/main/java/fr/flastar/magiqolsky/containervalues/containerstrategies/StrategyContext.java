package fr.flastar.magiqolsky.containervalues.containerstrategies;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class StrategyContext {
    private final AbstractContainerMenu handler;
    private final Component title;
    private final @Nullable Inventory playerInventory;
    private int backgroundHeight;

    public StrategyContext(AbstractContainerMenu handler, Component title, @Nullable Inventory playerInventory, int backgroundHeight) {
        this.handler = handler;
        this.title = title;
        this.playerInventory = playerInventory;
        this.backgroundHeight = backgroundHeight;
    }

    public AbstractContainerMenu handler() {
        return handler;
    }

    public Component title() {
        return title;
    }

    public @Nullable Inventory playerInventory() {
        return playerInventory;
    }

    public int backgroundHeight() {
        return backgroundHeight;
    }

    public void updateBackgroundHeight(int backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }
}
