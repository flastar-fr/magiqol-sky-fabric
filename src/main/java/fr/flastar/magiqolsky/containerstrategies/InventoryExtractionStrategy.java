package fr.flastar.magiqolsky.containerstrategies;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface InventoryExtractionStrategy {
    @Nullable
    Inventory extract(ScreenHandler handler);

    boolean supports(ScreenHandler handler, Text title);

    boolean isInventory();
}
