package fr.flastar.magiqolsky.shopitems.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.shopitems.model.ShopItem;
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

public class ShopConfig {

    private static final Gson GSON = new Gson();
    private static ShopConfig instance;

    private final Map<String, Float> shopItems = new HashMap<>();

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
        String jsonUrl = "https://raw.githubusercontent.com/flastar-fr/magiqol-sky-fabric/master/database/items.json";
        Language lang = Language.getInstance();

        try (InputStream inputStream = new URI(jsonUrl).toURL().openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Type type = new TypeToken<List<ShopItem>>() {}.getType();
            List<ShopItem> items = GSON.fromJson(reader, type);

            if (items == null || items.isEmpty()) {
                MagiQoLSky.LOGGER.error(lang.get("log.magiqol-sky.shop.no_items"));
                return;
            }

            for (ShopItem item : items) {
                shopItems.put(item.getId(), item.getSell());
            }

            String loadedMsg = String.format(lang.get("log.magiqol-sky.shop.loaded"), shopItems.size());
            MagiQoLSky.LOGGER.info(loadedMsg);

        } catch (IOException e) {
            String errorMsg = String.format(lang.get("log.magiqol-sky.shop.error"), jsonUrl);
            MagiQoLSky.LOGGER.error(errorMsg, e);
        } catch (URISyntaxException e) {
            MagiQoLSky.LOGGER.error(lang.get("log.magiqol-sky.shop.url_error"), e);
        }
    }

    public Map<String, Float> getAllShopItems() {
        return shopItems;
    }
}
