package fr.flastar.magiqolsky.shopitems.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.shopitems.model.ShopCategory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
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
        try {
            Path configDir = Path.of("config", "magiqolsky");
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }

            File configFile = configDir.resolve("shop_categories.json").toFile();

            if (!configFile.exists()) {
                try (FileWriter writer = new FileWriter(configFile)) {
                    writer.write("{\"categories\":[]}");
                }
            }

            try (FileReader reader = new FileReader(configFile)) {
                Type type = new TypeToken<Map<String, List<ShopCategory>>>() {}.getType();
                Map<String, List<ShopCategory>> config = GSON.fromJson(reader, type);

                if (config != null && config.containsKey("categories")) {
                    List<ShopCategory> categories = config.get("categories");

                    for (ShopCategory category : categories) {
                        Map<String, Float> categoryItems = new HashMap<>();

                        category.getItems().forEach(item ->
                                categoryItems.put(item.getId(), item.getPrice())
                        );

                        shopItems.put(category.getName().toLowerCase(), categoryItems);
                    }
                }
            }

        } catch (IOException e) {
            MagiQoLSky.LOGGER.error("Failed to load shop configuration", e);
        }
    }

    public Map<String, Float> getCategoryItems(String categoryName) {
        return shopItems.get(categoryName.toLowerCase());
    }

    public Map<String, Map<String, Float>> getAllShopItems() {
        return shopItems;
    }
}
