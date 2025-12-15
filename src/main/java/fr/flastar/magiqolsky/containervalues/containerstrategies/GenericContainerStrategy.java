package fr.flastar.magiqolsky.containervalues.containerstrategies;

import fr.flastar.magiqolsky.utils.FloatToString;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static fr.flastar.magiqolsky.containervalues.ContainerValueCalculator.getContainerTotalValue;
import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.DESIRED_PRECISION;

public class GenericContainerStrategy implements InventoryManagementStrategy {
    private static final int CHEST_SIZE = 9 * 3;
    private static final int DOUBLE_CHEST_SIZE = CHEST_SIZE * 2;

    private Text amountText = Text.of("");

    private final List<String> ACCEPTED_CONTAINER_KEYS = List.of(
            "block.minecraft.chest",
            "container.chestDouble",
            "block.minecraft.barrel"
    );

    @Override
    public boolean supports(ScreenHandler handler, Text title) {
        ArrayList<String> translatedNames = new ArrayList<>();

        // note: to garanty that a server GUI does not have a value calculated
        ACCEPTED_CONTAINER_KEYS.forEach(key -> translatedNames.add(I18n.translate(key)));

        return handler instanceof GenericContainerScreenHandler && translatedNames.contains(title.getString());
    }

    @Override
    public void render(DrawContext context, TextRenderer textRenderer, int color, int topCornerX, int topCornerY) {
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
        if (!(handler instanceof GenericContainerScreenHandler containerHandler)) {
            return null;
        }

        Inventory inventory = containerHandler.getInventory();
        int size = inventory.size();

        if (size != CHEST_SIZE && size != DOUBLE_CHEST_SIZE) {
            return null;
        }

        return inventory;
    }
}
