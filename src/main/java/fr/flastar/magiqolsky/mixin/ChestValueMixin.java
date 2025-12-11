package fr.flastar.magiqolsky.mixin;


import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.mixin.accessors.HandledScreenAccessor;
import fr.flastar.magiqolsky.mixin.accessors.ScreenAccessor;
import fr.flastar.magiqolsky.widget.ChestValueWidget;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class ChestValueMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        ScreenAccessor screenAccessor = (ScreenAccessor) this;
        HandledScreenAccessor handledScreenAccessor = (HandledScreenAccessor) this;

        ScreenHandler handler = ((HandledScreen<?>) (Object) this).getScreenHandler();

        if (!(handler instanceof GenericContainerScreenHandler chestHandler)) {
            return;
        }

        Inventory chestInventory = chestHandler.getInventory();
        int amountItems = 0;
        MagiQoLSky.LOGGER.info("Chest inventory size: " + chestInventory.size());

        for (int i = 0; i < chestInventory.size(); i++) {
            ItemStack stack = chestInventory.getStack(i);

            MagiQoLSky.LOGGER.info("Item: " + stack.getName().getString() + " - Count: " + stack.getCount());

            amountItems += stack.getCount();
        }

        int x = handledScreenAccessor.x();
        int y = handledScreenAccessor.y();

        ChestValueWidget chestValueWidget = new ChestValueWidget(x, y, 100, 100, Text.of("Test " + amountItems));

        screenAccessor.invokeAddDrawableChild(chestValueWidget);
    }
}
