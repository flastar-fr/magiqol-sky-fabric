package fr.flastar.magiqolsky.mixin.accessors;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {
    @Accessor("leftPos")
    int x();

    @Accessor("topPos")
    int y();

    @Accessor("imageWidth")
    int backgroundWidth();

    @Accessor("imageHeight")
    int backgroundHeight();
}
