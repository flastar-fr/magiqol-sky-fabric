package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public interface InventoryManagementStrategy {
    @Nullable
    Inventory extract(StrategyContext context);

    boolean supports(StrategyContext context);

    void render(DrawContext context, int color, Coordinates topCornerCoordinates);

    void update(StrategyContext strategyContext);

    Text getContainerAmountText();
}
