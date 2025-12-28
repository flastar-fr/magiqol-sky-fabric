package fr.flastar.magiqolsky;

import fr.flastar.magiqolsky.shopitems.ShopItemCreator;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.flastar.magiqolsky.chatmanager.ChatManager.registerChatFeatures;

public class MagiQoLSky implements ModInitializer {
	public static final String MOD_ID = "magiqol-sky";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ShopItemCreator shopItemCreator = new ShopItemCreator();

	@Override
	public void onInitialize() {
        shopItemCreator.createShopsItems();

		registerChatFeatures();
	}
}
