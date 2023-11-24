package io.github.krieven.stacker.sample.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.sample.model.CategoryLink;
import io.github.krieven.stacker.sample.model.Category;
import io.github.krieven.stacker.sample.model.Product;
import io.github.krieven.stacker.sample.model.KeyVal;
import io.github.krieven.stacker.util.ListUtils;

import java.util.List;
import java.util.Map;

public class Stub {
    //category, [key, title]
    enum FieldKey {
        FORCE, SIZE, DENSITY, WIRELESS
    }

    enum Cat {
        COMP,
        DESKTOP,
        LAPTOP,
        ADD,
        DISPLAY,
        KEYBOARD,
        MOUSE,
    }

    static final List<Category> categories = ImmutableList.of(
            new Category(Cat.COMP.name(), null, "Computer"),
            new Category(Cat.DESKTOP.name(), Cat.COMP.name(), "Desktop"),
            new Category(Cat.LAPTOP.name(), Cat.COMP.name(), "Laptop"),
            new Category(Cat.ADD.name(), null, "Additional devices"),
            new Category(Cat.DISPLAY.name(), Cat.ADD.name(), "Display"),
            new Category(Cat.KEYBOARD.name(), Cat.ADD.name(), "Keyboard"),
            new Category(Cat.MOUSE.name(), Cat.ADD.name(), "Mouse")
    );

    static final List<CategoryLink> additionals = ImmutableList.of(
            CategoryLink.build(Cat.DESKTOP.name(), Cat.DISPLAY.name()),
            CategoryLink.build(Cat.DESKTOP.name(), Cat.KEYBOARD.name()),
            CategoryLink.build(Cat.DESKTOP.name(), Cat.MOUSE.name()),
            CategoryLink.build(Cat.LAPTOP.name(), Cat.MOUSE.name())
    );

    static final Map<String, List<KeyVal>> fieldNames = ImmutableMap.of(
            Cat.DESKTOP.name(), ImmutableList.of(
                    KeyVal.build(FieldKey.FORCE.name(), "Performance"),
                    KeyVal.build(FieldKey.SIZE.name(), "Size")
            ),
            Cat.LAPTOP.name(), ImmutableList.of(
                    KeyVal.build(FieldKey.FORCE.name(), "Performance"),
                    KeyVal.build(FieldKey.SIZE.name(), "Size")
            ),
            Cat.DISPLAY.name(), ImmutableList.of(
                    KeyVal.build(FieldKey.SIZE.name(), "Size"),
                    KeyVal.build(FieldKey.DENSITY.name(), "Screen Density")
            ),
            Cat.KEYBOARD.name(), ImmutableList.of(
                    KeyVal.build(FieldKey.SIZE.name(), "Size"),
                    KeyVal.build(FieldKey.WIRELESS.name(), "Wireless")
            ),
            Cat.MOUSE.name(), ImmutableList.of(
                    KeyVal.build(FieldKey.WIRELESS.name(), "Wireless")
            )
    );

    static final List<Product> products = ListUtils.aListOf(
            //DESKTOP
            Product.build("1", Cat.DESKTOP.name(), "SuperPower Inc 98759", 1010,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("2", Cat.DESKTOP.name(), "SuperPower Inc 98760", 1200,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("3", Cat.DESKTOP.name(), "SuperPower Inc 98768", 999.99,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("4", Cat.DESKTOP.name(), "SuperPower Inc 98769", 1000,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("5", Cat.DESKTOP.name(), "SuperPower Inc 98770", 1250,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("6", Cat.DESKTOP.name(), "SuperPower Inc 98771", 1320,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("7", Cat.DESKTOP.name(), "SuperPower Inc 98772", 1123,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("8", Cat.DESKTOP.name(), "SuperPower Inc 98773", 1430,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            //LAPTOP
            Product.build("9", Cat.LAPTOP.name(), "D Go 0159", 1010,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("10", Cat.LAPTOP.name(), "D Go 0160", 1200,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("11", Cat.LAPTOP.name(), "D Go 0168", 999.99,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("12", Cat.LAPTOP.name(), "D Go 0169", 1000,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("13", Cat.LAPTOP.name(), "D Go 0170", 1250,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("14", Cat.LAPTOP.name(), "D Go 0171", 1320,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("15", Cat.LAPTOP.name(), "D Go 0172", 1123,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("16", Cat.LAPTOP.name(), "D Go 0173", 1430,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            //KEYBOARD
            Product.build("17", Cat.KEYBOARD.name(), "W Tap 01", 10,
                    ImmutableMap.of(
                            FieldKey.WIRELESS.name(), new Product.Field(Product.Field.Type.BOOLEAN, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("18", Cat.KEYBOARD.name(), "W Tap 01", 12,
                    ImmutableMap.of(
                            FieldKey.WIRELESS.name(), new Product.Field(Product.Field.Type.BOOLEAN, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            ),
            Product.build("19", Cat.KEYBOARD.name(), "W Tap 01", 1.50,
                    ImmutableMap.of(
                            FieldKey.WIRELESS.name(), new Product.Field(Product.Field.Type.BOOLEAN, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            )
    );

}
