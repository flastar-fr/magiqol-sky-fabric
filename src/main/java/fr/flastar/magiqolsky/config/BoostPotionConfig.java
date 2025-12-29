package fr.flastar.magiqolsky.config;

import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.api.AbstractRemoteJsonConfig;
import fr.flastar.magiqolsky.config.data.BoostPotion;

import java.util.List;

public class BoostPotionConfig
        extends AbstractRemoteJsonConfig<BoostPotion, String, BoostPotion> {

    private static BoostPotionConfig instance;

    private static final String JSON_URL =
            "https://raw.githubusercontent.com/flastar-fr/magiqol-sky-fabric/master/database/boost_potions.json";

    private BoostPotionConfig() {}

    public static synchronized BoostPotionConfig getInstance() {
        if (instance == null) {
            instance = new BoostPotionConfig();
        }
        return instance;
    }

    @Override
    protected String getJsonUrl() {
        return JSON_URL;
    }

    @Override
    protected TypeToken<List<BoostPotion>> getListType() {
        return new TypeToken<>() {};
    }

    @Override
    protected void mapItem(BoostPotion item) {
        values.put(item.id(), item);
    }
}
