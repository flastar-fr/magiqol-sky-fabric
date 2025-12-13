package fr.flastar.magiqolsky.shopitems.shopcategories;

public enum Farmer implements IShopCategory {
    WHEAT("minecraft:wheat", 3.f),
    BEETROOT("minecraft:beetroot", 3.f),
    POTATO("minecraft:potato", .75f),
    CARROT("minecraft:carrot", .75f),
    MELON_SLICE("minecraft:melon_slice", .23f),
    PUMPKIN("minecraft:pumpkin", 1.5f),
    SUGAR_CANE("minecraft:sugar_cane", .75f),
    WHEAT_SEEDS("minecraft:wheat_seeds", .23f),
    BEETROOT_SEEDS("minecraft:beetroot_seeds", .23f),
    POISONOUS_POTATO("minecraft:poisonous_potato", 1.6f),
    BAMBOO("minecraft:bamboo", .1f),
    KELP("minecraft:kelp", .1f),
    DRIED_KELP("minecraft:dried_kelp", .4f),
    CACTUS("minecraft:cactus", .25f),
    VINES("minecraft:vine", 1.5f),
    SWEAT_BERRIES("minecraft:sweet_berries", 1.f),
    GLOW_BERRIES("minecraft:glow_berries", 1.5f),
    HONEYCOMB("minecraft:honeycomb", 3.f),
    NETHER_WART("minecraft:nether_wart", .9f),
    NETHER_WART_BLOCK("minecraft:nether_wart_block", 4.f),
    WEEPING_VINES("minecraft:weeping_vines", .1f),
    TWISTING_VINES("minecraft:twisting_vines", .1f),
    PITCHER_PLANT("minecraft:pitcher_plant", 12.f),
    TORCHFLOWER("minecraft:torchflower", 12.f);

    private final String id;
    private final float price;

    Farmer(String id, float price) {
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
