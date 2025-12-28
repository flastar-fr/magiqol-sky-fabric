package fr.flastar.magiqolsky.customfoods.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.customfoods.model.CustomFood;
import net.minecraft.util.Language;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFoodConfig {

    private static final Gson GSON = new Gson();
    private static CustomFoodConfig instance;

    private final Map<String, CustomFood> customFoods = new HashMap<>();

    private static final String JSON_URL = "https://raw.githubusercontent.com/flastar-fr/magiqol-sky-fabric/master/database/custom_foods.json";

    private CustomFoodConfig() {
        loadConfig();
    }

    public static synchronized CustomFoodConfig getInstance() {
        if (instance == null) {
            instance = new CustomFoodConfig();
        }
        return instance;
    }

    private void loadConfig() {
        Language lang = Language.getInstance();

        try (InputStream inputStream = new URI(JSON_URL).toURL().openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Type type = new TypeToken<List<CustomFood>>() {}.getType();
            List<CustomFood> items = GSON.fromJson(reader, type);

            if (items == null || items.isEmpty()) {
                MagiQoLSky.LOGGER.error(lang.get("log.magiqol-sky.customfoods.no_items"));
                return;
            }

            for (CustomFood item : items) {
                customFoods.put(item.id(), item);
            }

            String loadedMsg = String.format(lang.get("log.magiqol-sky.customfoods.loaded"), customFoods.size());
            MagiQoLSky.LOGGER.info(loadedMsg);

        } catch (IOException e) {
            String errorMsg = String.format(lang.get("log.magiqol-sky.customfoods.error"), JSON_URL);
            MagiQoLSky.LOGGER.error(errorMsg, e);
        } catch (URISyntaxException e) {
            MagiQoLSky.LOGGER.error(lang.get("log.magiqol-sky.customfoods.url_error"), e);
        }
    }

    public Map<String, CustomFood> getCustomFoods() {
        return customFoods;
    }
}
