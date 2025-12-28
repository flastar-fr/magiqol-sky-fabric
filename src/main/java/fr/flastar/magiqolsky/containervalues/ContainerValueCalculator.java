package fr.flastar.magiqolsky.containervalues;

import fr.flastar.magiqolsky.MagiQoLSky;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;

import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

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
}
