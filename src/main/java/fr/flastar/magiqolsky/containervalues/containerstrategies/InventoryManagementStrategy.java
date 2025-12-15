package fr.flastar.magiqolsky.containervalues.containerstrategies;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface InventoryManagementStrategy {
    @Nullable
    Inventory extract(ScreenHandler handler);

    boolean supports(ScreenHandler handler, Text title);

    void render(DrawContext context, TextRenderer textRenderer, int color, int topCornerX, int topCornerY);

    void update(ScreenHandler handler);

    Text getAmountText();
}
