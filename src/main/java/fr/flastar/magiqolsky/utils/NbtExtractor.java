package fr.flastar.magiqolsky.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NbtExtractor {
    public static String extractPluginIdentifier(ItemStack itemStack) {
        NbtComponent customData = itemStack.getComponents().get(DataComponentTypes.CUSTOM_DATA);

        if (customData == null) {
            return "";
        }

        NbtCompound nbt = customData.copyNbt();
        if (!nbt.contains("PublicBukkitValues", NbtCompound.COMPOUND_TYPE)) {
            return "";
        }

        NbtCompound publicBukkitValues = nbt.getCompound("PublicBukkitValues");

        for (String nbtKey : publicBukkitValues.getKeys()) {
            if (nbtKey.contains(":")) {
                String[] parts = nbtKey.split(":", 2);
                String namespace = parts[0];

                if (publicBukkitValues.contains(nbtKey, NbtCompound.STRING_TYPE)) {
                    String pathValue = publicBukkitValues.getString(nbtKey);
                    return namespace + ":" + pathValue;
                }
            }
        }

        return "";
    }
}
