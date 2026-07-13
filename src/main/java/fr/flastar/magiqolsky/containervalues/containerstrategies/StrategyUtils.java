package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.Container;
import net.minecraft.network.chat.Component;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig.*;
import static fr.flastar.magiqolsky.utils.FloatToString.convertDecimalFloatToString;

public class StrategyUtils {

    public static void render2ContainersValues(GuiGraphicsExtractor context, Coordinates topCornerCoordinates, Component containerTextAmount, Component inventoryTextAmount, int color, int backgroundHeight) {
        Font font = Minecraft.getInstance().font;

        Coordinates containerTextCoordinate = new Coordinates(topCornerCoordinates.x() - TEXT_X_OFFSET - font.width(containerTextAmount),
                topCornerCoordinates.y() + TEXT_Y);

        context.text(
                font,
                containerTextAmount,
                containerTextCoordinate.x(),
                containerTextCoordinate.y(),
                color,
                false
        );

        Coordinates inventoryTextCoordinate = new Coordinates(
                containerTextCoordinate.x() + font.width(containerTextAmount) - font.width(inventoryTextAmount),
                topCornerCoordinates.y() + backgroundHeight - INVENTORY_CONTAINER_OFFSET_FROM_BOTTOM);

        context.text(
                font,
                inventoryTextAmount,
                inventoryTextCoordinate.x(),
                inventoryTextCoordinate.y(),
                color,
                false
        );
    }

    public static Component retrieveContainerAmountText(Container container) {
        float totalValue = getContainerTotalValue(container);

        char decimalSeparator = ContainerValueConfig.getConfig().decimalSeparator();
        char groupingSeparator = ContainerValueConfig.getConfig().groupingSeparator();
        boolean enableGrouping = ContainerValueConfig.getConfig().enableGrouping();

        String stringifiedValue = convertDecimalFloatToString(totalValue, DESIRED_PRECISION, decimalSeparator, groupingSeparator, enableGrouping);

        return Component.literal(stringifiedValue);
    }
}