package sample.flow.catalog.states.product;

import javax.validation.constraints.NotNull;
public interface ProductData {
    @NotNull
    ProductStateModel getStateModel(ProductState productState);
}
