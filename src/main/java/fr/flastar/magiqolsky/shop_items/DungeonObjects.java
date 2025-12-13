package fr.flastar.magiqolsky.shop_items;

public enum DungeonObjects implements IShopItems {
    PARASITE_WING("nexo:parasite_wing", 3250.f),
    POISON_THORN("nexo:poison_thorn", 15500.f),
    MUTANT_PLANT_PETAL("nexo:mutant_plant_petal", 37500.f),
    DOG_NECKLESS("nexo:dog_neckless", 1750.f),
    BROKEN_GOAT_HORN("nexo:broken_goat_horn", 18775.f),
    ILLUSIONNER_HAT("nexo:illusionner_hat", 31525.f),
    TIKI_BEARD("nexo:tiki_beard", 4000.f),
    TIKI_CROWN("nexo:tiki_crown", 25000.f),
    TIKI_MASK("nexo:tiki_mask", 42500.f),
    TIKI_HAT("nexo:tiki_hat", 92500.f);

    private final String id;
    private final float price;

    DungeonObjects(String id, float price) {
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
