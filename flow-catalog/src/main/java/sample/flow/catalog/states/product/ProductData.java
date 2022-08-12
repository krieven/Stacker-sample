package sample.flow.catalog.states.product;

import sample.model.Product;

public interface ProductData {
    String takeProductCategoryId();
    void setProduct(Product product);
}
