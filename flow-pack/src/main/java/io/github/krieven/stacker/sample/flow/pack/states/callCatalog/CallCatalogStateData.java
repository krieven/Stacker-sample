package io.github.krieven.stacker.sample.flow.pack.states.callCatalog;

import io.github.krieven.stacker.sample.model.Product;

public interface CallCatalogStateData {
    CallCatalogStateModel getStateModel(CallCatalogState o);

    interface CallCatalogStateModel {
        String getCategoryId();
        void setProduct(Product product);
    }
}
