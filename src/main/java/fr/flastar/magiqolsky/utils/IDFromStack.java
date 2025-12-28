package fr.flastar.magiqolsky.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class IDFromStack {
    public static String retrieveIDFromStack(ItemStack stack) {
        NbtComponent nbtComponent = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) {
            Identifier itemIDIdentifier = Registries.ITEM.getId(stack.getItem());

            return itemIDIdentifier.toString();
        } else {
            return ItemIDExtractor.extractPluginIdentifier(stack);
        }
    }
}
