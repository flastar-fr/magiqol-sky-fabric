package fr.flastar.magiqolsky.shop_items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShopItemCreator {
    private HashMap<String, Float> shopItems = null;

    public void createShopsItems() {
        shopItems = new HashMap<>();

        createShopItem(Arrays.asList(Hunter.values()));
        createShopItem(Arrays.asList(Farmer.values()));
        createShopItem(Arrays.asList(Miner.values()));
        createShopItem(Arrays.asList(Lumberjack.values()));
        createShopItem(Arrays.asList(Fisherman.values()));
        createShopItem(Arrays.asList(Cooking.values()));
        createShopItem(Arrays.asList(Botanic.values()));
    }

    private <T extends IShopItems> void createShopItem(List<T> list) {
        for (T item : list) {
            shopItems.put(item.getID(), item.getPrice());
        }
    }

    public HashMap<String, Float> getShopItems() {
        return shopItems;
    }
}
