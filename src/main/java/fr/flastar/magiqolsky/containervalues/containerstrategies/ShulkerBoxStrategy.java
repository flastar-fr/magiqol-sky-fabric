package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxMenuAccessor;
import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.render2ContainersValues;
import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.retrieveContainerAmountText;

public class ShulkerBoxStrategy implements InventoryManagementStrategy {

    private Component containerTextAmount = Component.literal("");
    private Component inventoryTextAmount = Component.literal("");

    private StrategyContext strategyContext;

    @Override
    public boolean supports(StrategyContext strategyContext) {
        return strategyContext.handler() instanceof ShulkerBoxMenu;
    }

    @Override
    public @Nullable Container extract(StrategyContext strategyContext) {
        if (!(strategyContext.handler() instanceof ShulkerBoxMenu shulkerHandler)) {
            return null;
        }

        ShulkerBoxMenuAccessor accessor = (ShulkerBoxMenuAccessor) shulkerHandler;
        return accessor.container();
    }

    @Override
    public void render(GuiGraphicsExtractor context, int color, Coordinates topCornerCoordinates) {
        render2ContainersValues(context, topCornerCoordinates, containerTextAmount, inventoryTextAmount, color, strategyContext.backgroundHeight());
    }

    @Override
    public void update(StrategyContext strategyContext) {
        this.strategyContext = strategyContext;
        Container containerInventory = extract(strategyContext);
        if (containerInventory == null) return;
        containerTextAmount = retrieveContainerAmountText(containerInventory);

        if (strategyContext.playerInventory() == null) return;
        inventoryTextAmount = retrieveContainerAmountText(strategyContext.playerInventory());
    }
}
