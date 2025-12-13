package fr.flastar.magiqolsky.shopitems.model;

import java.util.List;

public class ShopCategory {
    private String name;
    private List<ShopItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public void setItems(List<ShopItem> items) {
        this.items = items;
    }
}
