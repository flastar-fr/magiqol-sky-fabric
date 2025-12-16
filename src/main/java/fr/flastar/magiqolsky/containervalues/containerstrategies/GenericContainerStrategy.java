package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.render2ContainersValues;
import static fr.flastar.magiqolsky.containervalues.containerstrategies.StrategyUtils.retrieveContainerAmountText;

public class GenericContainerStrategy implements InventoryManagementStrategy {

    private Text containerTextAmount = Text.of("");
    private Text inventoryTextAmount = Text.of("");

    private final List<String> ACCEPTED_CONTAINER_KEYS = List.of(
            "block.minecraft.chest",
            "container.chestDouble",
            "block.minecraft.barrel"
    );

    private StrategyContext strategyContext;

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
    public void render(DrawContext context, int color, Coordinates topCornerCoordinates) {
        render2ContainersValues(context, topCornerCoordinates, containerTextAmount, inventoryTextAmount, color, strategyContext.backgroundHeight());
    }

    @Override
    public void update(StrategyContext strategyContext) {
        this.strategyContext = strategyContext;
        Inventory containerInventory = extract(strategyContext);
        if (containerInventory == null) return;
        containerTextAmount = retrieveContainerAmountText(containerInventory);

        if (strategyContext.playerInventory() == null) return;
        inventoryTextAmount = retrieveContainerAmountText(strategyContext.playerInventory());
    }

    @Override
    public Text getContainerAmountText() {
        return containerTextAmount;
    }
}
