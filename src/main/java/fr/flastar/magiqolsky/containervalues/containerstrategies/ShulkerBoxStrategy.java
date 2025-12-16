package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxScreenHandlerAccessor;
import fr.flastar.magiqolsky.utils.Coordinates;
import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.*;

public class ShulkerBoxStrategy implements InventoryManagementStrategy {

    private Text containerTextAmount = Text.of("");
    private Text inventoryTextAmount = Text.of("");

    @Override
    public boolean supports(StrategyContext strategyContext) {
        return strategyContext.handler() instanceof ShulkerBoxScreenHandler;
    }

    @Override
    public @Nullable Inventory extract(StrategyContext strategyContext) {
        if (!(strategyContext.handler() instanceof ShulkerBoxScreenHandler shulkerHandler)) {
            return null;
        }

        ShulkerBoxScreenHandlerAccessor accessor = (ShulkerBoxScreenHandlerAccessor) shulkerHandler;
        return accessor.inventory();
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, int color, Coordinates topCornerCoordinates) {
        Coordinates containerTextCoordinate = new Coordinates(topCornerCoordinates.x() - TEXT_X_OFFSET - textRenderer.getWidth(containerTextAmount),
                topCornerCoordinates.y() + TEXT_Y);

        context.drawText(
                textRenderer,
                containerTextAmount,
                containerTextCoordinate.x(),
                containerTextCoordinate.y(),
                color,
                false
        );

        Coordinates inventoryTextCoordinate = new Coordinates(
                containerTextCoordinate.x() + textRenderer.getWidth(containerTextAmount) - textRenderer.getWidth(inventoryTextAmount),
                INVENTORY_TEXT_Y_OFFSET + containerTextCoordinate.y() + TEXT_Y / 2);

        context.drawText(
                textRenderer,
                inventoryTextAmount,
                inventoryTextCoordinate.x(),
                inventoryTextCoordinate.y(),
                color,
                false
        );
    }

    @Override
    public void update(StrategyContext strategyContext) {
        Inventory containerInventory = extract(strategyContext);
        if (containerInventory == null) return;

        float totalValue = getContainerTotalValue(containerInventory);

        String stringifiedValue = FloatToString.convertDecimalFloatToString(totalValue, DESIRED_PRECISION);

        containerTextAmount = Text.of(stringifiedValue);

        if (strategyContext.playerInventory() == null) return;
        float inventoryTotalValue = getContainerTotalValue(strategyContext.playerInventory());

        String stringifiedInventoryValue = FloatToString.convertDecimalFloatToString(inventoryTotalValue, DESIRED_PRECISION);

        inventoryTextAmount = Text.of(stringifiedInventoryValue);
    }

    public Text getContainerAmountText() {
        return containerTextAmount;
    }
}
