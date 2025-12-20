package fr.flastar.magiqolsky.chatmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.flastar.magiqolsky.MagiQoLSky;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class AutoCommandConfig {
    public static final String[] ISLAND_COMMANDS = {"is", "island", "is go"};
    public static final String FLY_COMMAND = "fly";

    public static final int TIMEOUT_DELAY = 500;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("magiqolsky_autocommand_config.json").toFile();

    private static AutoCommandData currentConfig;

    public static AutoCommandData getConfig() {
        if (currentConfig == null) {
            load();
        }
        return currentConfig;
    }

    public synchronized static void load() {
        if (!CONFIG_FILE.exists()) {
            currentConfig = new AutoCommandData();
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            currentConfig = GSON.fromJson(reader, AutoCommandData.class);
        } catch (IOException e) {
            MagiQoLSky.LOGGER.error(
                    "Échec du chargement des données du compteur de mobs depuis {}",
                    CONFIG_FILE,
                    e
            );
            currentConfig = new AutoCommandData();
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
