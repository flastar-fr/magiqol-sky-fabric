package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.render2ContainersValues;
import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.retrieveContainerAmountText;

public class GenericContainerStrategy implements InventoryManagementStrategy {

    private Component containerTextAmount = Component.literal("");
    private Component inventoryTextAmount = Component.literal("");

    private final List<String> ACCEPTED_CONTAINER_KEYS = List.of(
            "block.minecraft.chest",
            "container.chestDouble",
            "block.minecraft.barrel",
            "block.minecraft.ender_chest"
    );

    private StrategyContext strategyContext;

    @Override
    public boolean supports(StrategyContext strategyContext) {
        ArrayList<String> translatedNames = new ArrayList<>();

        ACCEPTED_CONTAINER_KEYS.forEach(key -> translatedNames.add(I18n.get(key)));

        boolean isGenericContainer = strategyContext.handler() instanceof ChestMenu;
        boolean isAcceptedContainer = translatedNames.contains(strategyContext.title().getString());

        return isGenericContainer && isAcceptedContainer;
    }

    @Override
    public @Nullable Container extract(StrategyContext strategyContext) {
        if (!(strategyContext.handler() instanceof ChestMenu containerHandler)) {
            return null;
        }

        return containerHandler.getContainer();
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
