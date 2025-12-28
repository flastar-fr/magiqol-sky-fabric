package fr.flastar.magiqolsky.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class StackItems<T extends ClickableWidget> {
    private final Screen screen;
    private final int spacing;
    private final int sizeAdditionToWidget;
    private final int centerX;
    private final int y;
    private final List<T> items;

    private final int totalWidth;

    public StackItems(Screen screen, int centerX, int y, int spacing, int sizeAdditionToWidget, List<T> items) {
        this.screen = screen;
        this.spacing = spacing;
        this.sizeAdditionToWidget = sizeAdditionToWidget;
        this.centerX = centerX;
        this.y = y;
        this.items = items;

        this.totalWidth = getTotalWidth();
    }

    public void layout() {
        TextRenderer textRenderer = screen.getTextRenderer();

        int currentX = centerX - (totalWidth / 2);
        for (T item : items) {
            item.setX(currentX);
            item.setY(y);

            int itemWidth = textRenderer.getWidth(item.getMessage()) + sizeAdditionToWidget;
            currentX += itemWidth + spacing;
        }
    }

    public int getCorrespondingX(int index) {
        return items.get(index).getX();
    }

    public List<T> getItems() {
        return items;
    }

    private int getTotalWidth() {
        TextRenderer textRenderer = screen.getTextRenderer();

        int totalWidth = 0;
        for (T item : items) {
            totalWidth += textRenderer.getWidth(item.getMessage()) + sizeAdditionToWidget;
        }
        return totalWidth + (items.size() - 1) * spacing;
    }
}
