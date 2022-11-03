package sample.flow.catalog.states.product;

import org.jetbrains.annotations.NotNull;

public interface ProductData {
    @NotNull
    ProductStateModel getStateModel(ProductState productState);
}
