package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.DESIRED_PRECISION;

public class PlayerInventoryStrategy implements InventoryManagementStrategy {
    private Text amountText = Text.of("");

    @Unique
    private static final int INVENTORY_TEXT_Y_OFFSET = 65;

    @Override
    public boolean supports(ScreenHandler handler, Text title) {
        return handler instanceof PlayerScreenHandler;
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
    public void update(ScreenHandler handler) {
        Inventory containerInventory = extract(handler);
        if (containerInventory == null) return;

        float totalValue = getContainerTotalValue(containerInventory);

        String stringifiedValue = FloatToString.convertDecimalFloatToString(totalValue, DESIRED_PRECISION);

        amountText = Text.of(stringifiedValue);
    }

    @Override
    public Text getAmountText() {
        return amountText;
    }

    @Override
    @Nullable
    public Inventory extract(ScreenHandler handler) {
        if (!(handler instanceof PlayerScreenHandler playerHandler)) {
            return null;
        }

        if (playerHandler.slots.size() > PlayerScreenHandler.INVENTORY_START) {
            return playerHandler.slots.get(PlayerScreenHandler.INVENTORY_START).inventory;
        }
        return null;
    }
}
