package io.github.krieven.stacker.sample.flow.catalog.contract;

import java.math.BigDecimal;
import java.util.Map;

public class CatalogFlowRs {
    private String id;

    private String category;

    private String name;

    private BigDecimal price;

    private Map<String, CatalogFlowRs.Field> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<String, CatalogFlowRs.Field> getFields() {
        return fields;
    }

    public void setFields(Map<String, CatalogFlowRs.Field> fields) {
        this.fields = fields;
    }

    public static class Field {
        private String type;
        private  String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
