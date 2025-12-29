package fr.flastar.magiqolsky.config;

import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.api.AbstractRemoteJsonConfig;
import fr.flastar.magiqolsky.config.data.ShopItem;

import java.util.List;

public class ShopConfig
        extends AbstractRemoteJsonConfig<ShopItem, String, Float> {

    private static ShopConfig instance;

    private static final String JSON_URL =
            "https://raw.githubusercontent.com/flastar-fr/magiqol-sky-fabric/master/database/items.json";

    private ShopConfig() {}

    public static synchronized ShopConfig getInstance() {
        if (instance == null) {
            instance = new ShopConfig();
        }
        return instance;
    }

    @Override
    protected String getJsonUrl() {
        return JSON_URL;
    }

    @Override
    protected TypeToken<List<ShopItem>> getListType() {
        return new TypeToken<>() {};
    }

    @Override
    protected void mapItem(ShopItem item) {
        values.put(item.id(), item.sell());
    }
}
