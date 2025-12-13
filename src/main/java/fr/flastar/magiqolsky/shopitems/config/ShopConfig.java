package fr.flastar.magiqolsky.shopitems.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.shopitems.model.ShopCategory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopConfig {

    private static final Gson GSON = new Gson();
    private static ShopConfig instance;

    private final Map<String, Map<String, Float>> shopItems = new HashMap<>();

    private ShopConfig() {
        loadConfig();
    }

    public static synchronized ShopConfig getInstance() {
        if (instance == null) {
            instance = new ShopConfig();
        }
        return instance;
    }

    private void loadConfig() {
        String jsonUrl = "https://raw.githubusercontent.com/Lazerstricks/TestPublic/main/shop_categories.json";

        try (InputStream inputStream = new URL(jsonUrl).openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Type type = new TypeToken<Map<String, List<ShopCategory>>>() {}.getType();
            Map<String, List<ShopCategory>> config = GSON.fromJson(reader, type);

            if (config == null || !config.containsKey("categories")) {
                MagiQoLSky.LOGGER.error("Format de configuration invalide : clé 'categories' absente");
                return;
            }

            for (ShopCategory category : config.get("categories")) {
                Map<String, Float> categoryItems = new HashMap<>();

                category.getItems().forEach(item ->
                        categoryItems.put(item.getId(), item.getPrice())
                );

                shopItems.put(category.getName().toLowerCase(), categoryItems);
            }

            MagiQoLSky.LOGGER.info("Configuration du shop chargée avec succès depuis " + jsonUrl);

        } catch (IOException e) {
            MagiQoLSky.LOGGER.error(
                    "Échec du chargement de la configuration du shop depuis " + jsonUrl,
                    e
            );
        }
    }

    public Map<String, Float> getCategoryItems(String categoryName) {
        return shopItems.get(categoryName.toLowerCase());
    }

    public Map<String, Map<String, Float>> getAllShopItems() {
        return shopItems;
    }
}
