package fr.flastar.magiqolsky.shop_items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShopItemCreator {
    private HashMap<String, Float> shopItems = null;

    public void createShopsItems() {
        shopItems = new HashMap<>();

        createShopItem(Arrays.asList(Hunter.values()));
    }

    private <T extends IShopItems> void createShopItem(List<T> list) {
        for (T item : list) {
            shopItems.put(item.getName(), item.getPrice());
        }
    }

    public HashMap<String, Float> getShopItems() {
        return shopItems;
    }
}
