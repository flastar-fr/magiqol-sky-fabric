package fr.flastar.magiqolsky.chatmanager.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.chatmanager.model.ChatManagerData;
import fr.flastar.magiqolsky.chatmanager.model.TextReplacerEntry;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatManagerConfig {
    public static final String[] ISLAND_COMMANDS = {"is", "island", "is go"};
    public static final String[] BIENVENUE_COMMANDS = {"b", "bienvenue"};
    public static final String FLY_COMMAND = "fly";
    public static final String NV_COMMAND = "nv";

    public static final String BIENVENUE_MESSAGE = "Bienvenue !";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("magiqolsky_autocommand_config.json").toFile();

    private static ChatManagerData currentConfig;

    public static ChatManagerData getConfig() {
        if (currentConfig == null) {
            load();
        }
        return currentConfig;
    }

    public synchronized static void load() {
        if (!CONFIG_FILE.exists()) {
            List<TextReplacerEntry> textReplacers = new ArrayList<>();
            currentConfig = new ChatManagerData(textReplacers);
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            currentConfig = GSON.fromJson(reader, ChatManagerData.class);
            if (currentConfig != null) {
                currentConfig.rebuildCache();
            }
        } catch (IOException e) {
            String errorMsg = String.format("Failed to load Chat Manager configuration from %s", CONFIG_FILE.getName());
            MagiQoLSky.LOGGER.error(errorMsg, e);
            currentConfig = new ChatManagerData();
        }
    }

    public static void save() {
        currentConfig.makeSavable();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(currentConfig, writer);
        } catch (IOException e) {
            String errorMsg = String.format("Failed to save Chat Manager configuration to %s", CONFIG_FILE.getName());
            MagiQoLSky.LOGGER.error(errorMsg, e);
        }
    }
}
