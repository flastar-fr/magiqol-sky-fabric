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

    public static final int TIMEOUT_DELAY = 750;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("magiqolsky_autocommand_config.json").toFile();
    private static final String JSON_DEFAULT_PATH = "https://raw.githubusercontent.com/flastar-fr/magiqol-sky-fabric/master/default_emoji.json";

    private static ChatManagerData currentConfig;

    public static ChatManagerData getConfig() {
        if (currentConfig == null) {
            load();
        }
        return currentConfig;
    }

    public synchronized static void load() {
        if (!CONFIG_FILE.exists()) {
            List<TextReplacerEntry> textReplacers = loadDefaultEmoji();
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
            MagiQoLSky.LOGGER.error(
                    "Échec du chargement des données du compteur de mobs depuis {}",
                    CONFIG_FILE,
                    e
            );
            currentConfig = new ChatManagerData();
        }
    }

    private synchronized static List<TextReplacerEntry> loadDefaultEmoji() {
        try (InputStream inputStream = new URI(JSON_DEFAULT_PATH).toURL().openStream();
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            Type type = new TypeToken<List<TextReplacerEntry>>() {}.getType();
            List<TextReplacerEntry> emoji = GSON.fromJson(reader, type);

            MagiQoLSky.LOGGER.info("Configuration par défaut des emoji chargée");

            return emoji;

        } catch (IOException e) {
            MagiQoLSky.LOGGER.error(
                    "Échec du chargement de la configuration par défaut avec des emoji depuis {}",
                    JSON_DEFAULT_PATH,
                    e
            );
        } catch (URISyntaxException e) {
            MagiQoLSky.LOGGER.error("Erreur de syntaxe dans l'URL des emoji par défaut", e);
        }

        return new ArrayList<>();
    }

    public static void save() {
        currentConfig.makeSavable();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(currentConfig, writer);
        } catch (IOException e) {
            MagiQoLSky.LOGGER.error(
                    "Échec de la sauvegarde des données du compteur de mobs depuis {}",
                    CONFIG_FILE,
                    e
            );
        }
    }
}
