package sample.services;

import org.jetbrains.annotations.NotNull;
import sample.model.Product;
import sample.model.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogProductService {



    public Product getById(@NotNull String id){
        return Stub.products.stream().filter(item -> id.equals(item.getId())).findFirst().orElse(null);
    }

    public List<Product> getByCategory(@NotNull String category){
        return Stub.products.stream()
                .filter(item -> category.equals(item.getCaregory()))
                .sorted((a, b) -> a.compareTo(b)*-1).collect(Collectors.toList());
    }

    public List<Pair> getListNames(@NotNull String category){
        return Stub.fieldNames.get(category);
    }
}
