package fr.flastar.magiqolsky.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class ChestValueWidget extends ClickableWidget {
    private static final int TEXT_COLOR = 0xFFFFFF;

    public ChestValueWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        int textX = this.getX() + (this.getWidth() - textRenderer.getWidth(this.getMessage())) / 2;
        int textY = this.getY() + (this.getHeight() - textRenderer.fontHeight) / 2;

        context.drawTextWithShadow(
                textRenderer,
                this.getMessage(),
                textX,
                textY,
                TEXT_COLOR
        );
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }
}
