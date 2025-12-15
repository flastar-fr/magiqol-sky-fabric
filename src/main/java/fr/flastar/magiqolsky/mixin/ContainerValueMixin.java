package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.containervalues.containerstrategies.*;
import fr.flastar.magiqolsky.mixin.accessors.HandledScreenAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static fr.flastar.magiqolsky.containervalues.ContainerValueConfig.*;

@Mixin(HandledScreen.class)
public abstract class ContainerValueMixin {
    @Unique
    private final List<InventoryManagementStrategy> strategies = List.of(
            new GenericContainerStrategy(),
            new ShulkerBoxStrategy(),
            new PlayerInventoryStrategy(),
            new CraftingInventoryStrategy()
    );

    @Unique
    InventoryManagementStrategy currentStrategy = null;

    @Unique
    private Text cachedTitle = null;

    @Inject(method = "<init>(Lnet/minecraft/screen/ScreenHandler;Lnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/text/Text;)V", at = @At("RETURN"))
    private void cacheScreenTitle(ScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
        this.cachedTitle = title;
    }

    @Inject(method = "handledScreenTick", at = @At("HEAD"))
    private void updateContainerValue(CallbackInfo ci) {
        ScreenHandler handler = ((HandledScreen<?>) (Object) this).getScreenHandler();

        if (currentStrategy == null) {
            determineContainerInventory(handler);
        }

        if (currentStrategy != null) {
            currentStrategy.update(handler);
        }
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    protected void renderContainerValue(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (currentStrategy == null) {
            return;
        }

        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        int x = ((HandledScreenAccessor) screen).x();
        int y = ((HandledScreenAccessor) screen).y();
        int backgroundWidth = ((HandledScreenAccessor) screen).backgroundWidth();

        TextRenderer textRenderer = screen.getTextRenderer();
        Text amountText = currentStrategy.getAmountText();

        int topCornerX = x + backgroundWidth - TEXT_X_OFFSET - textRenderer.getWidth(amountText);
        int topCornerY = y + TEXT_Y;

        currentStrategy.render(context, textRenderer, TEXT_COLOR, topCornerX, topCornerY);
    }

    @Unique
    private void determineContainerInventory(ScreenHandler handler) {
        if (handler == null) {
            return;
        }

        for (InventoryManagementStrategy strategy : strategies) {
            if (strategy.supports(handler, cachedTitle)) {
                Inventory containerInventory = strategy.extract(handler);

                if (containerInventory != null) {
                    currentStrategy = strategy;
                    return;
                }
                return;
            }
        }
    }
}
