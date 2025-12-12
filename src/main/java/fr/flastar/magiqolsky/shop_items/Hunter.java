package fr.flastar.magiqolsky.shop_items;

public enum Hunter implements IShopItems {
    BONE("minecraft:bone", 0.2f),
    PANDA_TEETH("nexo:panda_teeth", 375.f);

    private final String id;
    private final float price;

    Hunter(String id, float price) {
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
