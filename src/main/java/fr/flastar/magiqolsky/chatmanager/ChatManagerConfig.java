package fr.flastar.magiqolsky.chatmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.flastar.magiqolsky.MagiQoLSky;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class ChatManagerConfig {
    public static final String[] ISLAND_COMMANDS = {"is", "island", "is go"};
    public static final String[] BIENVENUE_COMMANDS = {"b", "bienvenue"};
    public static final String FLY_COMMAND = "fly";

    public static final String BIENVENUE_MESSAGE = "Bienvenue !";

    public static final int TIMEOUT_DELAY = 500;

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
            currentConfig = new ChatManagerData();
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            currentConfig = GSON.fromJson(reader, ChatManagerData.class);
        } catch (IOException e) {
            MagiQoLSky.LOGGER.error(
                    "Échec du chargement des données du compteur de mobs depuis {}",
                    CONFIG_FILE,
                    e
            );
            currentConfig = new ChatManagerData();
        }
    }

    public static void save() {
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
