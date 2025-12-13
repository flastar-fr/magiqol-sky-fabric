package fr.flastar.magiqolsky.shopitems.shopcategories;

public enum Botanic implements IShopCategory {
    LILY_PAD("minecraft:lily_pad", 1.f),
    BIG_DRIPLEAF("minecraft:big_dripleaf", 1.f),
    SMALL_DRIPLEAF("minecraft:small_dripleaf", 1.f);

    private final String id;
    private final float price;

    Botanic(String id, float price) {
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
