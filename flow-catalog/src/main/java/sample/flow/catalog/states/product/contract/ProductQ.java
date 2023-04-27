package sample.flow.catalog.states.product.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import sample.model.KeyVal;
import sample.model.Product;

import java.util.List;

public class ProductQ {
    @JsonProperty(required = true)
    private List<ProductFilter> productFilters;

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
}
