package fr.flastar.magiqolsky.container_strategies;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public interface InventoryExtractionStrategy {
    @Nullable
    Inventory extract(ScreenHandler handler);

    boolean supports(ScreenHandler handler);

    boolean isInventory();
}
