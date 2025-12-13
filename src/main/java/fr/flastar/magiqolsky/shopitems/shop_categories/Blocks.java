package fr.flastar.magiqolsky.shopitems.shop_categories;

public enum Blocks implements IShopCategory {
    SNOW_BLOCK("minecraft:snow_block", .5f),
    ICE("minecraft:ice", 1.f),
    PRISMARINE("minecraft:prismarine", 2.f),
    PRISMARINE_BRICKS("minecraft:prismarine_bricks", 6.f),
    DARK_PRISMARINE("minecraft:dark_prismarine", 4.f),
    SEA_LANTERN("minecraft:sea_lantern", 1.6f),
    SHROOMLIGHT("minecraft:shroomlight", 2.f),
    DRIPSTONE_BLOCK("minecraft:dripstone_block", 5.f);

    private final String id;
    private final float price;

    Blocks(String id, float price) {
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
