package fr.flastar.magiqolsky.shopitems.shopcategories;

public enum Miner implements IShopCategory {
    COBBLESTONE("minecraft:cobblestone", .2f),
    STONE("minecraft:stone", .2f),
    DIORITE("minecraft:diorite", .2f),
    ANDESITE("minecraft:andesite", .2f),
    GRANITE("minecraft:granite", .2f),
    DEEPSLATE("minecraft:deepslate", .2f),
    TUFF("minecraft:tuff", .05f),
    MAGIC_GEM("nexo:magic_gem", 5.f),
    COAL("minecraft:coal", 1.f),
    COPPER_INGOT("minecraft:copper_ingot", .4f),
    IRON_INGOT("minecraft:iron_ingot", 2.4f),
    LAPIS_LAZULI("minecraft:lapis_lazuli", .9f),
    REDSTONE("minecraft:redstone", 2.4f),
    GOLD_INGOT("minecraft:gold_ingot", 8.f),
    EMERALD("minecraft:emerald", 10.f),
    DIAMOND("minecraft:diamond", 12.f),
    NETHER_QUARTZ("minecraft:quartz", 2.f),
    AMETHYST_SHARD("minecraft:amethyst_shard", 1.f);

    private final String id;
    private final float price;

    Miner(String id, float price) {
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
