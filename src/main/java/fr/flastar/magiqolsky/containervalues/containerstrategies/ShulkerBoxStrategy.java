package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxScreenHandlerAccessor;
import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.DESIRED_PRECISION;
import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;

public class ShulkerBoxStrategy implements InventoryManagementStrategy {

    private Text amountText = Text.of("");

    @Override
    public boolean supports(ScreenHandler handler, Text title) {
        return handler instanceof ShulkerBoxScreenHandler;
    }

    @Override
    public @Nullable Inventory extract(ScreenHandler handler) {
        if (!(handler instanceof ShulkerBoxScreenHandler shulkerHandler)) {
            return null;
        }

        ShulkerBoxScreenHandlerAccessor accessor = (ShulkerBoxScreenHandlerAccessor) shulkerHandler;
        return accessor.inventory();
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, int color, int topCornerX, int topCornerY) {
        context.drawText(
                textRenderer,
                amountText,
                topCornerX,
                topCornerY,
                color,
                false
        );
    }

    @Override
    public void update(ScreenHandler handler) {
        Inventory containerInventory = extract(handler);
        if (containerInventory == null) return;

        float totalValue = getContainerTotalValue(containerInventory);

        String stringifiedValue = FloatToString.convertDecimalFloatToString(totalValue, DESIRED_PRECISION);

        amountText = Text.of(stringifiedValue);
    }

    public Text getAmountText() {
        return amountText;
    }
}
