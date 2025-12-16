package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxScreenHandlerAccessor;
import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.render2ContainersValues;
import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.retrieveContainerAmountText;

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
    public void render(DrawContext context, int color, Coordinates topCornerCoordinates) {
        render2ContainersValues(context, topCornerCoordinates, containerTextAmount, inventoryTextAmount, color);
    }

    @Override
    public void update(StrategyContext strategyContext) {
        Inventory containerInventory = extract(strategyContext);
        if (containerInventory == null) return;
        containerTextAmount = retrieveContainerAmountText(containerInventory);

        if (strategyContext.playerInventory() == null) return;
        inventoryTextAmount = retrieveContainerAmountText(strategyContext.playerInventory());
    }

    public Text getContainerAmountText() {
        return containerTextAmount;
    }
}
