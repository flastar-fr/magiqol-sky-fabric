package fr.flastar.magiqolsky.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.CraftingScreenHandlerAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class CraftingInventoryStrategy implements InventoryExtractionStrategy {
    @Override
    @Nullable
    public Inventory extract(ScreenHandler handler) {
        if (!(handler instanceof CraftingScreenHandler craftingHandler)) {
            return null;
        }

        CraftingScreenHandlerAccessor accessor = (CraftingScreenHandlerAccessor) craftingHandler;
        PlayerEntity player = accessor.invokeGetPlayer();

        return player.getInventory();
    }

    @Override
    public boolean supports(ScreenHandler handler, Text title) {
        return handler instanceof CraftingScreenHandler;
    }

    @Override
    public boolean isInventory() {
        return true;
    }
}
