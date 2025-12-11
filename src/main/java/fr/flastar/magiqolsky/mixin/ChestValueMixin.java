package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.mixin.accessors.HandledScreenAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class ChestValueMixin {
    @Unique
    private static final int TEXT_Y = 6;

    @Unique
    private static final int TEXT_X_OFFSET = 8;

    @Unique
    private static final int TEXT_COLOR = 0x404040;

    @Unique
    private Text amountText = null;

    @Inject(method = "handledScreenTick", at = @At("HEAD"))
    private void updateChestValue(CallbackInfo ci) {
        ScreenHandler handler = ((HandledScreen<?>) (Object) this).getScreenHandler();

        if (!(handler instanceof GenericContainerScreenHandler chestHandler)) {
            this.amountText = null;
            return;
        }

        Inventory chestInventory = chestHandler.getInventory();
        int amountItems = 0;

        for (int i = 0; i < chestInventory.size(); i++) {
            ItemStack stack = chestInventory.getStack(i);

            if (!stack.isEmpty()) {
                amountItems += stack.getCount();
            }
        }

        this.amountText = Text.of(Integer.toString(amountItems));
    }

    @Inject(method = "drawForeground", at = @At(value = "HEAD"))
    protected void drawForeground(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.amountText == null) {
            return;
        }

        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        int screenX = ((HandledScreenAccessor) screen).backgroundWidth();

        TextRenderer textRenderer = screen.getTextRenderer();

        int textX = screenX - TEXT_X_OFFSET - textRenderer.getWidth(this.amountText);

        context.drawText(
                textRenderer,
                this.amountText,
                textX,
                TEXT_Y,
                TEXT_COLOR,
                false
        );
    }
}
