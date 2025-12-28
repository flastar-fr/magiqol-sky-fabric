package fr.flastar.magiqolsky.containervalues;

import fr.flastar.magiqolsky.MagiQoLSky;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Language;

import java.io.*;
import java.util.Locale;

import static fr.flastar.magiqolsky.utils.ClientLocaleUtils.getClientLocale;
import static net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil.GSON;

public class ContainerValueConfig {
    public static int DESIRED_PRECISION = 2;

    public static final int INVENTORY_TEXT_Y_OFFSET = 65;

    public static final int TEXT_Y = 6;

    public static final int TEXT_X_OFFSET = 8;

    public static final int TEXT_COLOR = 0x404040;

    public static final int INVENTORY_CONTAINER_OFFSET_FROM_BOTTOM = 94;

    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("magiqolsky_containervalue_config.json").toFile();
    private static ContainerValueData currentConfig;

    public static ContainerValueData getConfig() {
        if (currentConfig == null) {
            load();
        }
        return currentConfig;
    }

    public synchronized static void load() {
        Language lang = Language.getInstance();
        Locale locale = getClientLocale();

        if (!CONFIG_FILE.exists()) {
            currentConfig = new ContainerValueData(locale);
            save();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            currentConfig = GSON.fromJson(reader, ContainerValueData.class);
        } catch (IOException e) {
            String errorMsg = String.format(lang.get("log.magiqol-sky.containervalue.load_error"), CONFIG_FILE.getName());
            MagiQoLSky.LOGGER.error(errorMsg, e);
            currentConfig = new ContainerValueData(locale);
        }
    }

    public static void save() {
        Language lang = Language.getInstance();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(currentConfig, writer);
        } catch (IOException e) {
            String errorMsg = String.format(lang.get("log.magiqol-sky.containervalue.save_error"), CONFIG_FILE.getName());
            MagiQoLSky.LOGGER.error(errorMsg, e);
        }
    }
}
