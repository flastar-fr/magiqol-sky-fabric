package fr.flastar.magiqolsky.shopitems.shop_categories;

public enum Lumberjack implements IShopCategory {
    OAK_LOG("minecraft:oak_log", 3.f),
    BIRCH_LOG("minecraft:birch_log", 3.f),
    SPRUCE_LOG("minecraft:spruce_log", 1.5f),
    ACACIA_LOG("minecraft:acacia_log", 3.f),
    JUNGLE_LOG("minecraft:jungle_log", 1.5f),
    DARK_OAK_LOG("minecraft:dark_oak_log", 1.5f),
    MANGROVE_LOG("minecraft:mangrove_log", 3.f),
    CHERRY_LOG("minecraft:cherry_log", 3.f),
    CRIMSON_STEM("minecraft:crimson_stem", 10.f),
    WARPED_STEM("minecraft:warped_stem", 10.f),
    PALE_OAK_LOG("minecraft:pale_oak_log", 3.f),
    OAK_SAPPLING("minecraft:oak_sapling", .5f),
    BIRCH_SAPPLING("minecraft:birch_sapling", .5f),
    SPRUCE_SAPPLING("minecraft:spruce_sapling", .5f),
    ACACIA_SAPPLING("minecraft:acacia_sapling", .5f),
    JUNGLE_SAPPLING("minecraft:jungle_sapling", .5f),
    DARK_OAK_SAPPLING("minecraft:dark_oak_sapling", .5f),
    MANGROVE_PROPAGULE("minecraft:mangrove_propagule", .5f),
    CHERRY_SAPPLING("minecraft:cherry_sapling", 3.f),
    PALE_OAK_SAPPLING("minecraft:pale_oak_sapling", 3.f),
    AZALEA("minecraft:azalea", 3.f),
    FLOWERING_AZALEA("minecraft:flowering_azalea", 3.f),
    APPLE("minecraft:apple", 8.f),
    ANT("nexo:ant", 25.5f),
    CENTIPEDE("nexo:centipede", 25.5f),
    FIREBUG("nexo:firebug", 25.5f),
    GREEN_FLY("nexo:green_fly", 25.5f),
    SNAIL("nexo:snail", 25.5f),
    ORANGE_DRAGONFLY("nexo:orange_dragonfly", 25.5f),
    PURPLE_DRAGONFLY("nexo:purple_dragonfly", 25.5f),
    RED_DRAGONFLY("nexo:red_dragonfly", 25.5f),
    GREEN_BEETLE("nexo:green_beetle", 25.5f),
    SPIDER("nexo:spider", 25.5f),
    WORM("nexo:worm", 25.5f),
    BLUE_FLY("nexo:blue_fly", 25.5f),
    GREEN_BUTTERFLY("nexo:green_butterfly", 38.5f),
    RED_BUTTERFLY("nexo:red_butterfly", 38.5f),
    PINK_BUTTERFLY("nexo:pink_butterfly", 38.5f),
    CICADA("nexo:cicada", 38.5f),
    BLUE_BEETLE("nexo:blue_beetle", 38.5f),
    YELLOW_SPIDER("nexo:yellow_spider", 38.5f),
    FIREFLY("nexo:firefly", 57.5f),
    GOLD_BEETLE("nexo:gold_beetle", 57.5f),
    GIANT_HORNET("nexo:giant_hornet", 57.5f),
    TITAN_BEETLE("nexo:titan_beetle", 57.5f),
    LADYBUG("nexo:ladybug", 57.5f);

    private final String id;
    private final float price;

    Lumberjack(String id, float price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public float getPrice() {
        return price;
    }
}
