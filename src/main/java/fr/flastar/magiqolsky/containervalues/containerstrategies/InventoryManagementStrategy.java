package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.Container;
import org.jetbrains.annotations.Nullable;

public interface InventoryManagementStrategy {
    @Nullable
    Container extract(StrategyContext context);

    boolean supports(StrategyContext context);

    void render(GuiGraphicsExtractor context, int color, Coordinates topCornerCoordinates);

    void update(StrategyContext strategyContext);
}
