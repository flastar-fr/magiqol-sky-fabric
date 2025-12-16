package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.*;

public class GenericContainerStrategy implements InventoryManagementStrategy {

    private Text containerTextAmount = Text.of("");
    private Text inventoryTextAmount = Text.of("");

    private final List<String> ACCEPTED_CONTAINER_KEYS = List.of(
            "block.minecraft.chest",
            "container.chestDouble",
            "block.minecraft.barrel"
    );

    @Override
    public boolean supports(StrategyContext strategyContext) {
        ArrayList<String> translatedNames = new ArrayList<>();

        // note: to garanty that a server GUI does not have a value calculated
        ACCEPTED_CONTAINER_KEYS.forEach(key -> translatedNames.add(I18n.translate(key)));

        boolean isGenericContainer = strategyContext.handler() instanceof GenericContainerScreenHandler;
        boolean isAcceptedContainer = translatedNames.contains(strategyContext.title().getString());

        return isGenericContainer && isAcceptedContainer;
    }

    @Override
    public @Nullable Inventory extract(StrategyContext strategyContext) {
        if (!(strategyContext.handler() instanceof GenericContainerScreenHandler containerHandler)) {
            return null;
        }

        return containerHandler.getInventory();
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, int color, int topCornerX, int topCornerY) {
        context.drawText(
                textRenderer,
                containerTextAmount,
                topCornerX,
                topCornerY,
                color,
                false
        );

        int inventoryX = topCornerX + textRenderer.getWidth(containerTextAmount) - textRenderer.getWidth(inventoryTextAmount);
        int inventoryY = INVENTORY_TEXT_Y_OFFSET + topCornerY + TEXT_Y / 2;
        
        context.drawText(
                textRenderer,
                inventoryTextAmount,
                inventoryX,
                inventoryY,
                color,
                false
        );
    }

    @Override
    public void update(StrategyContext strategyContext) {
        Inventory containerInventory = extract(strategyContext);
        if (containerInventory == null) return;

        float containerTotalValue = getContainerTotalValue(containerInventory);

        String stringifiedContainerValue = FloatToString.convertDecimalFloatToString(containerTotalValue, DESIRED_PRECISION);

        containerTextAmount = Text.of(stringifiedContainerValue);

        float inventoryTotalValue = getContainerTotalValue(containerInventory);

        String stringifiedInventoryValue = FloatToString.convertDecimalFloatToString(inventoryTotalValue, DESIRED_PRECISION);

        inventoryTextAmount = Text.of(stringifiedInventoryValue);
    }

    @Override
    public Text getContainerAmountText() {
        return containerTextAmount;
    }
}
