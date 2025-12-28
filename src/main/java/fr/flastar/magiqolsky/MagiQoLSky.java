package fr.flastar.magiqolsky;

import fr.flastar.magiqolsky.containervalues.gui.config.ContainerValueConfig;
import fr.flastar.magiqolsky.cooldowndisplay.CooldownDisplayHud;
import fr.flastar.magiqolsky.customfoods.CustomFoodCreator;
import fr.flastar.magiqolsky.shopitems.ShopItemCreator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.flastar.magiqolsky.chatmanager.ChatManager.registerChatFeatures;
import static fr.flastar.magiqolsky.containervalues.gui.ContainerValueConfigButton.registerContainerValueConfigurationButton;

public class MagiQoLSky implements ModInitializer {
	public static final String MOD_ID = "magiqol-sky";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ShopItemCreator shopItemCreator = new ShopItemCreator();
	public static final CustomFoodCreator customFoodCreator = new CustomFoodCreator();

	@Override
	public void onInitialize() {
		customFoodCreator.createCustomFoods();
		CooldownDisplayHud.register();

		shopItemCreator.createShopsItems();

		registerContainerValueLoading();

		registerChatFeatures();
	}

	private void registerContainerValueLoading() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> ContainerValueConfig.load());
		registerContainerValueConfigurationButton();
	}
}
