package io.github.krieven.stacker.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Pack {

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = Optional.ofNullable(products).orElse(new ArrayList<>()).stream()
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isEmpty() {
        return products.isEmpty();
    }
}
