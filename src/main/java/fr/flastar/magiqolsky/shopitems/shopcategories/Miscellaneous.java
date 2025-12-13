package fr.flastar.magiqolsky.shopitems.shopcategories;

public enum Miscellaneous implements IShopCategory {
    CREEPER_HEAD("minecraft:creeper_head", 50.f),
    WITHER_SKELETON_SKULL("minecraft:wither_skeleton_skull", 100.f),;

    private final String id;
    private final float price;

    Miscellaneous(String id, float price) {
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
