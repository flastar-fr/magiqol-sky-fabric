package fr.flastar.magiqolsky.container_strategies;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public class GenericContainerStrategy implements InventoryExtractionStrategy {
    private static final int CHEST_SIZE = 9 * 3;
    private static final int DOUBLE_CHEST_SIZE = CHEST_SIZE * 2;

    @Override
    public boolean supports(ScreenHandler handler) {
        return handler instanceof GenericContainerScreenHandler;
    }

    @Override
    public boolean isInventory() {
        return false;
    }

    @Override
    @Nullable
    public Inventory extract(ScreenHandler handler) {
        if (!(handler instanceof GenericContainerScreenHandler containerHandler)) {
            return null;
        }

        Inventory inventory = containerHandler.getInventory();
        int size = inventory.size();

        if (size != CHEST_SIZE && size != DOUBLE_CHEST_SIZE) {
            return null;
        }

        return inventory;
    }
}
