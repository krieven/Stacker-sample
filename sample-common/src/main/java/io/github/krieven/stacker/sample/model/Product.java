package io.github.krieven.stacker.sample.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public class Product implements Comparable<Product> {

    private String id;

    private String category;

    private String name;

    private BigDecimal price;

    private Map<String, Field> fields;

    public static Product build(String id, String caregory, String name, double price, Map<String, Field> fields) {
        return Product.build(id, caregory, name, BigDecimal.valueOf(price), fields);
    }

    public static Product build(String id, String caregory, String name, BigDecimal price, Map<String, Field> fields) {
        Product res = new Product();

        res.setId(id);
        res.setCategory(caregory);
        res.setName(name);
        res.setPrice(price);
        res.setFields(fields);
        return res;
    }

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

    public Map<String, Field> getFields() {
        return fields;
    }

    public void setFields(Map<String, Field> fields) {
        this.fields = fields;
    }

    @Override
    public int compareTo(@NotNull Product o) {
        return this.getPrice().compareTo(o.getPrice());
    }

    public static class Field implements Comparable<Field> {
        private Type type;
        private String value;

        public Field(@NotNull Type type, @NotNull String value) {
            this.setType(type);
            this.setValue(value);
        }

        public Field() {
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int compareTo(@NotNull Product.Field o) {
            if (this == o || this.equals(o)) {
                return 0;
            }
            if (this.getType() != o.getType() || this.getType() == Type.STRING) {
                return this.getValue().compareTo(o.getValue());
            }

            return new BigDecimal(this.getValue()).compareTo(new BigDecimal(o.getValue()));
        }

        public enum Type {
            STRING,
            NUMBER,
            BOOLEAN;
        }


    }
}
