package fr.flastar.magiqolsky.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Optional;

public class ItemIDExtractor {
    public static String extractPluginIdentifier(ItemStack itemStack) {
        CustomData customData = itemStack.getComponents().get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            return "";
        }

        CompoundTag tag = customData.copyTag();

        Optional<CompoundTag> publicBukkitValuesOpt = tag.getCompound("PublicBukkitValues");

        if (publicBukkitValuesOpt.isEmpty()) {
            return "";
        }

        CompoundTag publicBukkitValues = publicBukkitValuesOpt.get();

        for (String nbtKey : publicBukkitValues.keySet()) {
            if (nbtKey.contains(":")) {
                String[] parts = nbtKey.split(":", 2);
                String namespace = parts[0];

                if (publicBukkitValues.contains(nbtKey)) {
                    Optional<String> pathValue = publicBukkitValues.getString(nbtKey);
                    return namespace + ":" + pathValue.orElse("notfound");
                }
            }
        }
        return "";
    }
}
