package fr.flastar.magiqolsky.containervalues.containerstrategies;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public record StrategyContext(ScreenHandler handler, Text title, @Nullable PlayerInventory playerInventory) {}
