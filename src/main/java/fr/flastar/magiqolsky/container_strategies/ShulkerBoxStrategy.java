package fr.flastar.magiqolsky.container_strategies;

import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxScreenHandlerAccessor;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import org.jetbrains.annotations.Nullable;

public class ShulkerBoxStrategy implements InventoryExtractionStrategy {
    @Override
    public boolean supports(ScreenHandler handler) {
        return handler instanceof ShulkerBoxScreenHandler;
    }

    @Override
    public boolean isInventory() {
        return false;
    }

    @Override
    @Nullable
    public Inventory extract(ScreenHandler handler) {
        if (!(handler instanceof ShulkerBoxScreenHandler shulkerHandler)) {
            return null;
        }

        ShulkerBoxScreenHandlerAccessor accessor = (ShulkerBoxScreenHandlerAccessor) shulkerHandler;
        return accessor.inventory();
    }
}
