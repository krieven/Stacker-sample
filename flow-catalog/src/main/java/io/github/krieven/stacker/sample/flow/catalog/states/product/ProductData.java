package io.github.krieven.stacker.sample.flow.catalog.states.product;

import io.github.krieven.stacker.sample.model.Product;

import javax.validation.constraints.NotNull;
public interface ProductData {
    @NotNull
    ProductData.StateModel getStateModel(ProductState productState);

    interface StateModel {
        String getCategoryId();

        void setProduct(Product product);
    }
}
