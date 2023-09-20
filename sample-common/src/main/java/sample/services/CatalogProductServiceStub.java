package sample.services;

import javax.validation.constraints.NotNull;import sample.model.Product;
import sample.model.KeyVal;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogProductServiceStub implements CatalogProductService {



    @Override
    public Product getById(@NotNull String id){
        return Stub.products.stream().filter(item -> id.equals(item.getId())).findFirst().orElse(null);
    }

    @Override
    public List<Product> getByCategory(@NotNull String category){
        return Stub.products.stream()
                .filter(item -> category.equals(item.getCategory()))
                .sorted((a, b) -> a.compareTo(b)*-1).collect(Collectors.toList());
    }

    @Override
    public List<KeyVal> getFieldNamesByCategory(@NotNull String category){
        return Stub.fieldNames.get(category);
    }
}
