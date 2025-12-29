package fr.flastar.magiqolsky.config;

import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.api.AbstractRemoteJsonConfig;
import fr.flastar.magiqolsky.config.data.CustomFood;

import java.util.List;

public class CustomFoodConfig
        extends AbstractRemoteJsonConfig<CustomFood, String, CustomFood> {

    private static CustomFoodConfig instance;

    private static final String JSON_URL =
            "https://raw.githubusercontent.com/flastar-fr/magiqol-sky-fabric/master/database/custom_foods.json";

    private CustomFoodConfig() {}

    public static synchronized CustomFoodConfig getInstance() {
        if (instance == null) {
            instance = new CustomFoodConfig();
        }
        return instance;
    }

    @Override
    protected String getJsonUrl() {
        return JSON_URL;
    }

    @Override
    protected TypeToken<List<CustomFood>> getListType() {
        return new TypeToken<>() {};
    }

    @Override
    protected void mapItem(CustomFood item) {
        values.put(item.id(), item);
    }
}
