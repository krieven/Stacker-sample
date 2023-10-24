package io.github.krieven.stacker.sample.model;

import java.util.ArrayList;
import java.util.List;

public class Pack {
    private int id;
    private List<Product> products = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }
}
