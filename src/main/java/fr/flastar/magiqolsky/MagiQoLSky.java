package fr.flastar.magiqolsky;

import fr.flastar.magiqolsky.shop_items.ShopItemCreator;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagiQoLSky implements ModInitializer {
	public static final String MOD_ID = "magiqol-sky";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ShopItemCreator shopItemCreator = new ShopItemCreator();

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		shopItemCreator.createShopsItems();
	}
}
