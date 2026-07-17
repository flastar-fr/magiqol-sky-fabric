package fr.flastar.magiqolsky.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class IDFromStack {
    public static String retrieveIDFromStack(ItemStack stack) {
        CustomData customData = stack.getComponents().get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            Identifier itemIDLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());

            return itemIDLocation.toString();
        }
        return ItemIDExtractor.extractPluginIdentifier(stack);
    }
}
