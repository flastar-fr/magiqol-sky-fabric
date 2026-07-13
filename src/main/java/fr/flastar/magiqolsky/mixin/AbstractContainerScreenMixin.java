package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.containervalues.containerstrategies.*;
import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import fr.flastar.magiqolsky.mixin.accessors.AbstractContainerScreenAccessor;
import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig.TEXT_COLOR;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {
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
    private StrategyContext strategyContext;

    @Inject(method = "<init>(Lnet/minecraft/world/inventory/AbstractContainerMenu;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/network/chat/Component;)V", at = @At("RETURN"))
    private void cacheStrategyContext(AbstractContainerMenu handler, Inventory inventory, Component title, CallbackInfo ci) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        this.strategyContext = new StrategyContext(handler, title, inventory, ((AbstractContainerScreenAccessor) screen).backgroundHeight());
    }

    @Inject(method = "containerTick", at = @At("HEAD"))
    private void updateContainerValue(CallbackInfo ci) {
        AbstractContainerMenu handler = ((AbstractContainerScreen<?>) (Object) this).getMenu();

        if (currentStrategy == null) {
            determineContainerInventory(handler);
        }

        if (currentStrategy != null) {
            currentStrategy.update(strategyContext);
        }
    }

    @Inject(method = "extractContents", at = @At(value = "TAIL"))
    protected void renderContainerValue(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!ContainerValueConfig.getConfig().isContainerValueEnabled()) return;

        if (currentStrategy == null) {
            return;
        }

        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
        int x = ((AbstractContainerScreenAccessor) screen).x();
        int y = ((AbstractContainerScreenAccessor) screen).y();
        Coordinates screenCoordinates = new Coordinates(x, y);
        int backgroundWidth = ((AbstractContainerScreenAccessor) screen).backgroundWidth();
        strategyContext.updateBackgroundHeight(((AbstractContainerScreenAccessor) screen).backgroundHeight());

        Coordinates topCornerCoordinates = new Coordinates(x + backgroundWidth, screenCoordinates.y());

        currentStrategy.render(context, TEXT_COLOR, topCornerCoordinates);
    }

    @Unique
    private void determineContainerInventory(AbstractContainerMenu handler) {
        if (handler == null) {
            return;
        }

        for (InventoryManagementStrategy strategy : strategies) {
            if (strategy.supports(strategyContext)) {
                Container containerInventory = strategy.extract(strategyContext);

                if (containerInventory != null) {
                    currentStrategy = strategy;
                    return;
                }
                return;
            }
        }
    }
}