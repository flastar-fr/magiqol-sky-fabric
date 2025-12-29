package fr.flastar.magiqolsky;

import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import fr.flastar.magiqolsky.cooldowndisplay.CooldownDisplayHud;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.flastar.magiqolsky.chatmanager.ChatManager.registerChatFeatures;
import static fr.flastar.magiqolsky.containervalues.gui.ContainerValueConfigButton.registerContainerValueConfigurationButton;

public class MagiQoLSky implements ModInitializer {
	public static final String MOD_ID = "magiqol-sky";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CooldownDisplayHud.register();
		registerContainerValueLoading();
		registerChatFeatures();
	}

	private void registerContainerValueLoading() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> ContainerValueConfig.load());
		registerContainerValueConfigurationButton();
	}
}
