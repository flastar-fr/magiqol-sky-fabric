package fr.flastar.magiqolsky.mixin;


import fr.flastar.magiqolsky.mixin.accessors.HandledScreenAccessor;
import fr.flastar.magiqolsky.mixin.accessors.ScreenAccessor;
import fr.flastar.magiqolsky.widget.ChestValueWidget;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
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

        int x = handledScreenAccessor.x();
        int y = handledScreenAccessor.y();

        ChestValueWidget chestValueWidget = new ChestValueWidget(x, y, 100, 100, Text.of("Test"));

        screenAccessor.invokeAddDrawableChild(chestValueWidget);
    }
}
