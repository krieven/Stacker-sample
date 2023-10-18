package io.github.krieven.stacker.sample.flow.catalog.states.product.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.krieven.stacker.sample.model.KeyVal;

import java.util.List;

public class ProductQ {
    @JsonProperty(required = true)
    private List<ProductFilter> productFilters;
    private String categoryName;

    @JsonProperty(required = true)
    private List<KeyVal> fieldNames;

    public List<ProductFilter> getProductFilters() {
        return productFilters;
    }

    public void setProductFilters(List<ProductFilter> productFilters) {
        this.productFilters = productFilters;
    }

    public List<KeyVal> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<KeyVal> listNames) {
        fieldNames = listNames;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
