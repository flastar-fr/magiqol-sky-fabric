package fr.flastar.magiqolsky.containerstrategies;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

public class PlayerInventoryStrategy implements InventoryManagementStrategy {
    @Unique
    private static final int INVENTORY_TEXT_Y_OFFSET = 65;

    @Override
    public boolean supports(ScreenHandler handler, Text title) {
        return handler instanceof PlayerScreenHandler;
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
