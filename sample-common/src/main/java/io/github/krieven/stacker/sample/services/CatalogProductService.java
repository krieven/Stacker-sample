package io.github.krieven.stacker.sample.services;

import io.github.krieven.stacker.sample.model.KeyVal;
import io.github.krieven.stacker.sample.model.Product;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CatalogProductService {
    Product getById(@NotNull String id);

    List<Product> getByCategory(@NotNull String category);

    List<KeyVal> getFieldNamesByCategory(@NotNull String category);
}
