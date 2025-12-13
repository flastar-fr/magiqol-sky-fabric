package fr.flastar.magiqolsky.container_strategies;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public class PlayerInventoryStrategy implements InventoryExtractionStrategy {
    @Override
    public boolean supports(ScreenHandler handler) {
        return handler instanceof PlayerScreenHandler;
    }

    @Override
    public boolean isInventory() {
        return true;
    }

    @Override
    @Nullable
    public Inventory extract(ScreenHandler handler) {
        if (!(handler instanceof PlayerScreenHandler playerHandler)) {
            return null;
        }

        if (playerHandler.slots.size() > PlayerScreenHandler.INVENTORY_START) {
            return playerHandler.slots.get(PlayerScreenHandler.INVENTORY_START).inventory;
        }
        return null;
    }
}
