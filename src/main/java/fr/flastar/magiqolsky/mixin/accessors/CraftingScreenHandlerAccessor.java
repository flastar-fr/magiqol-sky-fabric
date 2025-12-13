package fr.flastar.magiqolsky.mixin.accessors;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CraftingScreenHandler.class)
public interface CraftingScreenHandlerAccessor {
    @Invoker("getPlayer")
    PlayerEntity invokeGetPlayer();
}
