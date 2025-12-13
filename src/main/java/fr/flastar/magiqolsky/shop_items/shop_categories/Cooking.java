package fr.flastar.magiqolsky.shop_items.shop_categories;

public enum Cooking implements IShopCategory {
    WATERMELON_ICE_CREAM("nexo:watermelon_ice_cream", .5f),
    WATERMELON_SKEWER("nexo:watermelon_skewer", 1.f),
    VANILLA_MILKSHAKE("nexo:vanilla_milkshake", 2.5f),
    MELON_SORBET("nexo:melon_sorbet", 4.5f),
    BANANA_SMOOTHIE("nexo:banana_smoothie", 4.5f),
    VANILLA_ICE_CREAM("nexo:vanilla_ice_cream", 5.f),
    CHOCOLATE_ICE_CREAM("nexo:chocolate_ice_cream", 5.5f),
    CHOCOLATE_PINEAPPLE("nexo:chocolate_pineapple", 5.5f),
    CHOCOLATE_STRAWBERRY("nexo:chocolate_strawberry", 7.5f),
    TIRAMISU("nexo:tiramisu", 9.5f),
    STRAWBERRY_ICE_CREAM("nexo:strawberry_ice_cream", 9.5f),
    BANANA_ICE_CREAM("nexo:banana_ice_cream", 9.5f),
    HONEY_RIBS("nexo:honey_ribs", 11.f),
    CHOCOLATE_DONUT("nexo:chocolate_donut", 12.5f),
    PINEAPPLE_CHICKEN("nexo:pineapple_chicken", 14.f),
    STRAWBERRY_DONUT("nexo:strawberry_donut", 14.5f),
    VANILLA_CREAM("nexo:vanilla_cream", 17.f),
    CHOCOLATE_MARBLE_CAKE("nexo:chocolate_marble_cake", 18.f),
    COCONUT_JUICE("nexo:coconut_juice", 19.5f),
    HAWAIIAN_PIZZA("nexo:hawaiian_pizza", 22.f),
    GINGERBREAD("nexo:gingerbread", 22.5f),
    STRAWBERRY_CAKE("nexo:strawberry_cake", 24.f),
    BANANA_SPLIT("nexo:banana_split", 30.f),
    BANANA_BREAD("nexo:banana_bread", 31.f),
    COCONUT_ICE_CREAM("nexo:coconut_ice_cream", 37.f),
    COCONUT_CAKE("nexo:coconut_cake", 60.f),
    WATERMELON_SKEWER_GOLD("nexo:watermelon_skewer_gold", 4485.f),
    CHOCOLATE_MARBLE_CAKE_GOLD("nexo:chocolate_marble_cake_gold", 8280.f),
    VANILLA_CREAM_GOLD("nexo:vanilla_cream_gold", 41400.f),
    STRAWBERRY_CAKE_GOLD("nexo:strawberry_cake_gold", 55200.f),
    GINGERBREAD_GOLD("nexo:gingerbread_gold", 55200.f),
    COCONUT_CAKE_GOLD("nexo:coconut_cake_gold", 69000.f),
    HAWAIIAN_PIZZA_GOLD("nexo:hawaiian_pizza_gold", 82800.f),
    BANANA_BREAD_GOLD("nexo:banana_bread_gold", 82800.f);

    private final String id;
    private final float price;

    Cooking(String id, float price) {
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
