package sample.flow.catalog.states.product;

import sample.model.Product;

public class ProductStateModel {
    private String categoryId;
    private Product product;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
