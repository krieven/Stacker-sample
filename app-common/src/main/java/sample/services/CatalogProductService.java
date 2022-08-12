package sample.services;

import org.jetbrains.annotations.NotNull;
import sample.model.KeyVal;
import sample.model.Product;

import java.util.List;

public interface CatalogProductService {
    Product getById(@NotNull String id);

    List<Product> getByCategory(@NotNull String category);

    List<KeyVal> getFieldNamesByCategory(@NotNull String category);
}
