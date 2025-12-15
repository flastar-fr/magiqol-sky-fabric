package fr.flastar.magiqolsky.containervalues;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.utils.ItemIDExtractor;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;

public class ContainerValueCalculator {
    @Unique
    public static float getContainerTotalValue(Inventory containerInventory) {
        float totalValue = 0;
        HashMap<String, Float> shopItems = MagiQoLSky.shopItemCreator.getShopItems();

        for (int i = 0; i < containerInventory.size(); i++) {
            ItemStack stack = containerInventory.getStack(i);

            String itemID = retrieveIDFromStack(stack);

            if (!shopItems.containsKey(itemID)) {
                continue;
            }

            int amountItems = stack.getCount();
            float itemValue = shopItems.get(itemID);

            totalValue += amountItems * itemValue;
        }
        return totalValue;
    }

    @Unique
    private static String retrieveIDFromStack(ItemStack stack) {
        NbtComponent nbtComponent = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) {
            Identifier itemIDIdentifier = Registries.ITEM.getId(stack.getItem());

            return itemIDIdentifier.toString();
        } else {
            return ItemIDExtractor.extractPluginIdentifier(stack);
        }
    }
}
