package fr.flastar.magiqolsky.shop_items;

public enum Hunter implements IShopItems {
    BONE("Bone", 0.2f);

    private final String name;
    private final float price;

    Hunter(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getPrice() {
        return price;
    }
}
