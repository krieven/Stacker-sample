package sample.services;

import sample.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CatalogCategoryService {

    public List<Category> getCategories() {
        return getCategories(null);
    }

    public List<Category> getCategories(String parent) {
        return Stub.categories.stream().filter(
                item -> (parent == null && item.getParent() == null) || (parent != null && parent.equals(item.getParent()))
        ).collect(Collectors.toList());
    }

    public Category getCategory(String categoryId) {
        return Stub.categories.stream().filter(
                item -> item.getId() != null && item.getId().equals(categoryId)
        ).findFirst().orElse(null);
    }

    public List<Category> getAdditionals(String categoryId) {
        List<String> list = Stub.additionals.stream().filter(
                item -> item.getCategory().equals(categoryId)
        ).map(
                AdditionalDevice::getAddCategory
        ).collect(Collectors.toList());

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        return Stub.categories.stream().filter(
                item -> list.contains(item.getId())
        ).collect(Collectors.toList());
    }

    static class AdditionalDevice {
        private String category;
        private String addCategory;

        AdditionalDevice(String category, String addCategory) {
            this.category = category;
            this.addCategory = addCategory;
        }

        private String getCategory() {
            return category;
        }

        private String getAddCategory() {
            return addCategory;
        }
    }
}
