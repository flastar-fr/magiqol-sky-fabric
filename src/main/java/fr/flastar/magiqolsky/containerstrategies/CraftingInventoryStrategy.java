package fr.flastar.magiqolsky.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.CraftingScreenHandlerAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

public class CraftingInventoryStrategy implements InventoryManagementStrategy {
    @Unique
    private static final int INVENTORY_TEXT_Y_OFFSET = 65;

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
    public void render(DrawContext context, TextRenderer textRenderer, Text text, int color, int topCornerX, int topCornerY) {
        topCornerY += INVENTORY_TEXT_Y_OFFSET;

        context.drawText(
                textRenderer,
                text,
                topCornerX,
                topCornerY,
                color,
                false
        );
    }
}
