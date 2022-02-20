package sample.flow.catalog.states.product.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import sample.model.Pair;
import sample.model.Product;

import java.util.List;

public class ProductQ {
    @JsonProperty(required = true)
    private
    List<ProductFilter> productFilters;

    @JsonProperty(required = true)
    private
    List<Product> products;

    @JsonProperty(required = true)
    private List<Pair> fieldNames;

    public List<ProductFilter> getProductFilters() {
        return productFilters;
    }

    public void setProductFilters(List<ProductFilter> productFilters) {
        this.productFilters = productFilters;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Pair> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<Pair> listNames) {
        fieldNames = listNames;
    }
}
