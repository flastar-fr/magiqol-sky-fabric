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
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.DESIRED_PRECISION;

public class GenericContainerStrategy implements InventoryManagementStrategy {

    private Text amountText = Text.of("");

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
                amountText,
                topCornerX,
                topCornerY,
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

        amountText = Text.of(stringifiedValue);
    }

    @Override
    public Text getAmountText() {
        return amountText;
    }
}
