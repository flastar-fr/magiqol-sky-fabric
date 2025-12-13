package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.mixin.accessors.HandledScreenAccessor;
import fr.flastar.magiqolsky.mixin.accessors.ShulkerBoxScreenHandlerAccessor;
import fr.flastar.magiqolsky.utils.FloatToString;
import fr.flastar.magiqolsky.utils.NbtExtractor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

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

        Inventory containerInventory;
        if (handler instanceof GenericContainerScreenHandler chestHandler) {
            containerInventory = chestHandler.getInventory();
        }
        else if (handler instanceof ShulkerBoxScreenHandler shulkerHandler) {
            ShulkerBoxScreenHandlerAccessor shulkerHandlerAccessor = (ShulkerBoxScreenHandlerAccessor) shulkerHandler;
            containerInventory = shulkerHandlerAccessor.inventory();
        } else {
            this.amountText = null;
            return;
        }

        float totalValue = 0;
        HashMap<String, Float> shopItems = MagiQoLSky.shopItemCreator.getShopItems();

        for (int i = 0; i < containerInventory.size(); i++) {
            ItemStack stack = containerInventory.getStack(i);

            String itemID = retrieveIDFromStack(stack);

            if (!shopItems.containsKey(itemID)) {
                continue;
            }

            int amountItems = stack.getCount();
            float itemValue = shopItems.get(itemID);

            totalValue += amountItems * itemValue;
        }

        String stringifiedValue = FloatToString.convertDecimalFloatToString(totalValue, 2);

        this.amountText = Text.of(stringifiedValue);
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

    @Unique
    private String retrieveIDFromStack(ItemStack stack) {
        NbtComponent nbtComponent = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) {
            Identifier itemIDIdentifier = Registries.ITEM.getId(stack.getItem());

            return itemIDIdentifier.toString();
        } else {
            return NbtExtractor.extractPluginIdentifier(stack);
        }
    }
}
