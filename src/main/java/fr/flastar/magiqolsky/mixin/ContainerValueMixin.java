package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.containerstrategies.*;
import fr.flastar.magiqolsky.mixin.accessors.HandledScreenAccessor;
import fr.flastar.magiqolsky.utils.FloatToString;
import fr.flastar.magiqolsky.utils.ItemIDExtractor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;

@Mixin(HandledScreen.class)
public abstract class ContainerValueMixin {

    @Unique private static final int TEXT_Y = 6;
    @Unique private static final int TEXT_X_OFFSET = 8;
    @Unique private static final int TEXT_COLOR = 0x404040;
    @Unique private static final int INVENTORY_TEXT_Y_OFFSET = 65;
    @Unique private static final int DESIRED_PRECISION = 2;

    @Unique
    private final List<InventoryExtractionStrategy> strategies = List.of(
            new GenericContainerStrategy(),
            new ShulkerBoxStrategy(),
            new PlayerInventoryStrategy(),
            new CraftingInventoryStrategy()
    );

    @Unique private boolean isInventory;
    @Unique private Text amountText;
    @Unique private Text cachedTitle;

    @Inject(
            method = "<init>(Lnet/minecraft/screen/ScreenHandler;Lnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/text/Text;)V",
            at = @At("RETURN")
    )
    private void cacheScreenTitle(ScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
        this.cachedTitle = title;
    }

    @Inject(method = "handledScreenTick", at = @At("HEAD"))
    private void updateContainerValue(CallbackInfo ci) {
        ScreenHandler handler = ((HandledScreen<?>) (Object) this).getScreenHandler();
        Inventory inventory = determineContainerInventory(handler);
        if (inventory == null) return;

        float totalValue = getContainerTotalValue(inventory);
        this.amountText = Text.of(
                FloatToString.format(totalValue, DESIRED_PRECISION)
        );
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderContainerValue(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (amountText == null) return;

        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        HandledScreenAccessor accessor = (HandledScreenAccessor) screen;

        TextRenderer renderer = screen.getTextRenderer();

        int x = accessor.x();
        int y = accessor.y();
        int width = accessor.backgroundWidth();

        int textX = x + width - TEXT_X_OFFSET - renderer.getWidth(amountText);
        int textY = y + TEXT_Y + (isInventory ? INVENTORY_TEXT_Y_OFFSET : 0);

        context.drawText(renderer, amountText, textX, textY, TEXT_COLOR, false);
    }

    @Unique
    private String retrieveIDFromStack(ItemStack stack) {
        NbtComponent customData = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);

        if (customData == null) {
            Identifier id = Registries.ITEM.getId(stack.getItem());
            return id.toString();
        }

        return ItemIDExtractor.extractPluginIdentifier(stack);
    }

    @Unique
    private float getContainerTotalValue(Inventory inventory) {
        float total = 0f;
        HashMap<String, Float> shopItems = MagiQoLSky.shopItemCreator.getShopItems();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            String itemID = retrieveIDFromStack(stack);

            Float value = shopItems.get(itemID);
            if (value == null) continue;

            total += stack.getCount() * value;
        }
        return total;
    }

    @Unique
    private @Nullable Inventory determineContainerInventory(ScreenHandler handler) {
        if (handler == null) {
            amountText = null;
            return null;
        }

        for (InventoryExtractionStrategy strategy : strategies) {
            if (!strategy.supports(handler, cachedTitle)) continue;

            Inventory inventory = strategy.extract(handler);
            if (inventory == null) {
                amountText = null;
                return null;
            }

            isInventory = strategy.isInventory();
            return inventory;
        }

        amountText = null;
        return null;
    }
}
