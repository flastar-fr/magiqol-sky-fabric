package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import static fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig.*;
import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.retrieveContainerAmountText;

public class PlayerInventoryStrategy implements InventoryManagementStrategy {

    private Component amountText = Component.literal("");

    @Override
    public boolean supports(StrategyContext strategyContext) {
        return strategyContext.handler() instanceof InventoryMenu;
    }

    @Override
    public @Nullable Container extract(StrategyContext strategyContext) {
        if (!(strategyContext.handler() instanceof InventoryMenu playerHandler)) {
            return null;
        }

        if (playerHandler.slots.size() > InventoryMenu.INV_SLOT_START) {
            return playerHandler.slots.get(InventoryMenu.INV_SLOT_START).container;
        }
        return null;
    }

    @Override
    public void render(GuiGraphicsExtractor context, int color, Coordinates topCornerCoordinates) {
        Font font = Minecraft.getInstance().font;

        context.text(
                font,
                amountText,
                topCornerCoordinates.x() - TEXT_X_OFFSET - font.width(amountText),
                topCornerCoordinates.y() + INVENTORY_TEXT_Y_OFFSET + TEXT_Y,
                color,
                false
        );
    }

    @Override
    public void update(StrategyContext strategyContext) {
        Container containerInventory = extract(strategyContext);
        if (containerInventory == null) return;
        amountText = retrieveContainerAmountText(containerInventory);
    }
}
