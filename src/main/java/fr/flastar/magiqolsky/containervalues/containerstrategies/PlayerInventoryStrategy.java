package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.DESIRED_PRECISION;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.INVENTORY_TEXT_Y_OFFSET;

public class PlayerInventoryStrategy implements InventoryManagementStrategy {

    private Text amountText = Text.of("");

    @Override
    public boolean supports(StrategyContext strategyContext) {
        return strategyContext.handler() instanceof PlayerScreenHandler;
    }

    @Override
    public @Nullable Inventory extract(StrategyContext strategyContext) {
        if (!(strategyContext.handler() instanceof PlayerScreenHandler playerHandler)) {
            return null;
        }

        if (playerHandler.slots.size() > PlayerScreenHandler.INVENTORY_START) {
            return playerHandler.slots.get(PlayerScreenHandler.INVENTORY_START).inventory;
        }
        return null;
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, int color, int topCornerX, int topCornerY) {
        topCornerY += INVENTORY_TEXT_Y_OFFSET;

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
    public Text getContainerAmountText() {
        return amountText;
    }
}
