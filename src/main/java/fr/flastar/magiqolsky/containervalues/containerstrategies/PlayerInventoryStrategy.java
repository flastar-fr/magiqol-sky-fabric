package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig.*;
import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.retrieveContainerAmountText;

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
    public void render(DrawContext context, int color, Coordinates topCornerCoordinates) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        context.drawText(
                textRenderer,
                amountText,
                topCornerCoordinates.x() - TEXT_X_OFFSET - textRenderer.getWidth(amountText),
                topCornerCoordinates.y() + INVENTORY_TEXT_Y_OFFSET + TEXT_Y,
                color,
                false
        );
    }

    @Override
    public void update(StrategyContext strategyContext) {
        Inventory containerInventory = extract(strategyContext);
        if (containerInventory == null) return;
        amountText = retrieveContainerAmountText(containerInventory);
    }

    @Override
    public Text getContainerAmountText() {
        return amountText;
    }
}
