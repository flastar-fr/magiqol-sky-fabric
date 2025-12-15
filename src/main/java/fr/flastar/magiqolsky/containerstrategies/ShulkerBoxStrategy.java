package fr.flastar.magiqolsky.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxScreenHandlerAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class ShulkerBoxStrategy implements InventoryManagementStrategy {
    @Override
    public boolean supports(ScreenHandler handler, Text title) {
        return handler instanceof ShulkerBoxScreenHandler;
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, Text text, int color, int topCornerX, int topCornerY) {
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
        if (!(handler instanceof ShulkerBoxScreenHandler shulkerHandler)) {
            return null;
        }

        ShulkerBoxScreenHandlerAccessor accessor = (ShulkerBoxScreenHandlerAccessor) shulkerHandler;
        return accessor.inventory();
    }
}
