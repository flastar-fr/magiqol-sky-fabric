package fr.flastar.magiqolsky.containervalues;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.config.ShopConfig;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;

import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

public class ContainerValueCalculator {
    @Unique
    public static float getContainerTotalValue(Inventory containerInventory) {
        float totalValue = 0;
        Map<String, Float> shopItems = ShopConfig.getInstance().getValues();

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
