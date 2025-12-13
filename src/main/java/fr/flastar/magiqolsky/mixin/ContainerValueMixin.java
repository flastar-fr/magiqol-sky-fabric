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
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(HandledScreen.class)
public abstract class ContainerValueMixin {
    @Unique
    private static final int TEXT_Y = 6;

    @Unique
    private static final int TEXT_X_OFFSET = 8;

    @Unique
    private static final int TEXT_COLOR = 0x404040;

    @Unique
    private static final int INVENTORY_TEXT_Y_OFFSET = 65;

    @Unique
    private boolean isInventory = false;

    @Unique
    private Text amountText = null;

    @Inject(method = "handledScreenTick", at = @At("HEAD"))
    private void updateContainerValue(CallbackInfo ci) {
        ScreenHandler handler = ((HandledScreen<?>) (Object) this).getScreenHandler();

        Inventory containerInventory = determineContainerInventory(handler);
        if (containerInventory == null) return;

        float totalValue = getContainerTotalValue(containerInventory);

        String stringifiedValue = FloatToString.convertDecimalFloatToString(totalValue, 2);

        this.amountText = Text.of(stringifiedValue);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    protected void renderContainerValue(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.amountText == null) {
            return;
        }

        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        int x = ((HandledScreenAccessor) screen).x();
        int y = ((HandledScreenAccessor) screen).y();
        int backgroundWidth = ((HandledScreenAccessor) screen).backgroundWidth();

        TextRenderer textRenderer = screen.getTextRenderer();

        int textX = x + backgroundWidth - TEXT_X_OFFSET - textRenderer.getWidth(this.amountText);
        int textY = y + TEXT_Y;

        if (isInventory) {
            textY += INVENTORY_TEXT_Y_OFFSET;
        }

        context.drawText(
                textRenderer,
                this.amountText,
                textX,
                textY,
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

    @Unique
    private float getContainerTotalValue(Inventory containerInventory) {
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
        return totalValue;
    }

    @Unique
    private @Nullable Inventory determineContainerInventory(ScreenHandler handler) {
        Inventory containerInventory;

        switch (handler) {
            case GenericContainerScreenHandler chestHandler -> containerInventory = chestHandler.getInventory();
            case ShulkerBoxScreenHandler shulkerHandler -> {
                ShulkerBoxScreenHandlerAccessor shulkerHandlerAccessor = (ShulkerBoxScreenHandlerAccessor) shulkerHandler;
                containerInventory = shulkerHandlerAccessor.inventory();
            }
            case PlayerScreenHandler playerHandler -> {
                if (playerHandler.slots.size() > PlayerScreenHandler.INVENTORY_START) {
                    containerInventory = playerHandler.slots.get(PlayerScreenHandler.INVENTORY_START).inventory;
                    isInventory = true;
                } else {
                    this.amountText = null;
                    return null;
                }
            }
            case null, default -> {
                this.amountText = null;
                return null;
            }
        }
        return containerInventory;
    }
}
