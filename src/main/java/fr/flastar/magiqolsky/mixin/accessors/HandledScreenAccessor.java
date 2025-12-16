package fr.flastar.magiqolsky.mixin.accessors;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor("x")
    int x();

    @Accessor("y")
    int y();

    @Accessor("backgroundWidth")
    int backgroundWidth();

    @Accessor("backgroundHeight")
    int backgroundHeight();
}
