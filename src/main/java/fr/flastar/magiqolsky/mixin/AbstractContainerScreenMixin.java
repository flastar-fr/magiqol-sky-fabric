package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.containervalues.containerstrategies.*;
import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import fr.flastar.magiqolsky.utils.Coordinates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig.TEXT_COLOR;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {
    @Shadow
    protected int leftPos;

    @Shadow
    protected int topPos;

    @Final
    @Shadow
    protected int imageWidth;

    @Final
    @Shadow
    protected int imageHeight;

    @Shadow
    @Final
    protected AbstractContainerMenu menu;

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

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;

        Minecraft client = Minecraft.getInstance();
        Inventory playerInventory = client.player != null ? client.player.getInventory() : null;

        strategyContext = new StrategyContext(menu, screen.getTitle(), playerInventory, imageHeight);

        determineContainerInventory(menu);
    }

    @Inject(method = "containerTick", at = @At("HEAD"))
    private void updateContainerValue(CallbackInfo ci) {
        if (strategyContext == null) {
            return;
        }

        AbstractContainerMenu handler = ((AbstractContainerScreen<?>) (Object) this).getMenu();

        if (currentStrategy == null) {
            determineContainerInventory(handler);
        } else {
            currentStrategy.update(strategyContext);
        }
    }

    @Inject(method = "extractContents", at = @At(value = "TAIL"))
    protected void renderContainerValue(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        if (!ContainerValueConfig.getConfig().isContainerValueEnabled()) return;

        if (currentStrategy == null || strategyContext == null) {
            return;
        }

        strategyContext.updateBackgroundHeight(imageHeight);

        int x = this.leftPos;
        int y = this.topPos;
        Coordinates topCornerCoordinates = new Coordinates(x + imageWidth, y);

        currentStrategy.render(graphics, TEXT_COLOR, topCornerCoordinates);
    }

    @Unique
    private void determineContainerInventory(AbstractContainerMenu handler) {
        if (handler == null || strategyContext == null) {
            return;
        }

        for (InventoryManagementStrategy strategy : strategies) {
            if (strategy.supports(strategyContext)) {
                Container containerInventory = strategy.extract(strategyContext);

                if (containerInventory != null) {
                    currentStrategy = strategy;

                    currentStrategy.update(strategyContext);
                    return;
                }
            }
        }

        currentStrategy = null;
    }
}
