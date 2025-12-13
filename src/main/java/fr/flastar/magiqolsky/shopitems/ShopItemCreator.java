package fr.flastar.magiqolsky.shopitems;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.shopitems.config.ShopConfig;

import java.util.HashMap;
import java.util.Map;

public class ShopItemCreator {
    private final HashMap<String, Float> shopItems = new HashMap<>();
    private final ShopConfig shopConfig;

    public ShopItemCreator() {
        this.shopConfig = ShopConfig.getInstance();
    }

    public void createShopsItems() {
        shopItems.clear();
        shopItems.putAll(shopConfig.getAllShopItems());
    }

    public HashMap<String, Float> getShopItems() {
        return shopItems;
    }
}
