package sample.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import sample.model.Category;
import sample.model.Product;
import sample.model.Pair;

import java.util.List;
import java.util.Map;

class Stub {
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

    static final List<CatalogCategoryService.AdditionalDevice> additionals = ImmutableList.of(
            new CatalogCategoryService.AdditionalDevice(Cat.DESKTOP.name(), Cat.DISPLAY.name()),
            new CatalogCategoryService.AdditionalDevice(Cat.DESKTOP.name(), Cat.KEYBOARD.name()),
            new CatalogCategoryService.AdditionalDevice(Cat.DESKTOP.name(), Cat.MOUSE.name()),
            new CatalogCategoryService.AdditionalDevice(Cat.LAPTOP.name(), Cat.MOUSE.name())
    );

    static final Map<String, List<Pair>> fieldNames = ImmutableMap.of(
            Cat.DESKTOP.name(), ImmutableList.of(
                    Pair.build(FieldKey.FORCE.name(), "Performance"),
                    Pair.build(FieldKey.SIZE.name(), "Size")
            ),
            Cat.LAPTOP.name(), ImmutableList.of(
                    Pair.build(FieldKey.FORCE.name(), "Performance"),
                    Pair.build(FieldKey.SIZE.name(), "Size")
            ),
            Cat.DISPLAY.name(), ImmutableList.of(
                    Pair.build(FieldKey.SIZE.name(), "Size"),
                    Pair.build(FieldKey.DENSITY.name(), "Screen Density")
            ),
            Cat.KEYBOARD.name(), ImmutableList.of(
                    Pair.build(FieldKey.SIZE.name(), "Size"),
                    Pair.build(FieldKey.WIRELESS.name(), "Wireless")
            ),
            Cat.MOUSE.name(), ImmutableList.of(
                    Pair.build(FieldKey.WIRELESS.name(), "Wireless")
            )
    );

    static final List<Product> products = ImmutableList.of(
            new Product("1", Cat.DESKTOP.name(), "SuperPower Inc 98769", 1000,
                    ImmutableMap.of(
                            FieldKey.FORCE.name(), new Product.Field(Product.Field.Type.NUMBER, "100"),
                            FieldKey.SIZE.name(), new Product.Field(Product.Field.Type.NUMBER, "100")
                    )
            )
    );
}
