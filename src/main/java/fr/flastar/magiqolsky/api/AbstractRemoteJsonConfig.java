package fr.flastar.magiqolsky.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.MagiQoLSky;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRemoteJsonConfig<T, K, V> {

    protected static final Gson GSON = new Gson();
    protected final Map<K, V> values = new HashMap<>();

    protected AbstractRemoteJsonConfig() {
        load();
    }

    protected abstract String getJsonUrl();
    protected abstract TypeToken<List<T>> getListType();
    protected abstract void mapItem(T item);

    private void load() {
        try (InputStream inputStream = new URI(getJsonUrl()).toURL().openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Type type = getListType().getType();
            List<T> items = GSON.fromJson(reader, type);

            if (items == null || items.isEmpty()) {
                MagiQoLSky.LOGGER.error("No items found in {}", getJsonUrl());
                return;
            }

            for (T item : items) {
                mapItem(item);
            }

            MagiQoLSky.LOGGER.info("{} loaded ({} items)",
                    getClass().getSimpleName(), values.size());

        } catch (Exception e) {
            MagiQoLSky.LOGGER.error("Failed to load config from {}", getJsonUrl(), e);
        }
    }

    public Map<K, V> getValues() {
        return values;
    }

    public V get(K key) {
        return values.get(key);
    }
}
