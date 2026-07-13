package fr.flastar.magiqolsky.utils;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.Optional;

public class ItemIDExtractor {
    public static String extractPluginIdentifier(ItemStack itemStack) {
        CustomData customData = itemStack.getComponents().get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            return "";
        }

        CompoundTag nbt = customData.copyTag();
        if (!nbt.contains("PublicBukkitValues") || nbt.getId() != Tag.TAG_COMPOUND) {
            return "";
        }

        Optional<CompoundTag> publicBukkitValuesOpt = nbt.getCompound("PublicBukkitValues");

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
                    return namespace + ":" + pathValue;
                }
            }
        }

        return "";
    }
}
