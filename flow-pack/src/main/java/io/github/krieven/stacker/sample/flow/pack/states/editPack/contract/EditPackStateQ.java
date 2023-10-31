package io.github.krieven.stacker.sample.flow.pack.states.editPack.contract;

import io.github.krieven.stacker.sample.model.Category;
import io.github.krieven.stacker.sample.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EditPackStateQ {

    private final List<Item> items = new ArrayList<>();
    private BigDecimal price = BigDecimal.ZERO;

    public void addItem(Product product) {
        Item item = new Item();

        item.setType(Item.Type.REM);
        item.setId(product.getId());
        item.setTitle(product.getName());
        item.setPrice(product.getPrice());
        price = price.add(product.getPrice());

        items.add(item);
    }

    public void addItem(Category category) {
        if(items.stream().anyMatch(item -> item.getId().equals(category.getId()))){
            return;
        }

        Item item = new Item();

        item.setType(Item.Type.ADD);
        item.setId(category.getId());
        item.setTitle(category.getTitle());

        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static class Item {
        private Type type;
        private String id;
        private String title;
        private BigDecimal price;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public enum Type {
            REM,
            ADD
        }
    }

}
