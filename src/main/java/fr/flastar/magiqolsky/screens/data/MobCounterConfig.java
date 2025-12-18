package fr.flastar.magiqolsky.screens.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.flastar.magiqolsky.MagiQoLSky;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MobCounterConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("magiqolsky_counter_data.json").toFile();

    private static TimerData currentConfig;

    public static TimerData getConfig() {
        if (currentConfig == null) {
            load();
        }
        return currentConfig;
    }

    public synchronized static void load() {
        if (!CONFIG_FILE.exists()) {
            currentConfig = new TimerData();
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            currentConfig = GSON.fromJson(reader, TimerData.class);
        } catch (IOException e) {
            MagiQoLSky.LOGGER.error(
                    "Échec du chargement des données du compteur de mobs depuis {}",
                    CONFIG_FILE,
                    e
            );
            currentConfig = new TimerData();
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
