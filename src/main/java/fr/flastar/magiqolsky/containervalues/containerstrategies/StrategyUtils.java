package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.*;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.TEXT_Y;

public class StrategyUtils {
    public static void render2ContainersValues(DrawContext context, Coordinates topCornerCoordinates, Text containerTextAmount, Text inventoryTextAmount, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

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
                containerTextCoordinate.y() + INVENTORY_TEXT_Y_OFFSET + TEXT_Y/2);

        context.drawText(
                textRenderer,
                inventoryTextAmount,
                inventoryTextCoordinate.x(),
                inventoryTextCoordinate.y(),
                color,
                false
        );
    }

    public static Text retrieveContainerAmountText(Inventory container) {
        float totalValue = getContainerTotalValue(container);

        String stringifiedValue = FloatToString.convertDecimalFloatToString(totalValue, DESIRED_PRECISION);

        return Text.of(stringifiedValue);
    }
}
