package sample.services;

import sample.model.CategoryLink;
import sample.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CatalogCategoryService {

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
                CategoryLink::getAddCategory
        ).collect(Collectors.toList());

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        return Stub.categories.stream().filter(
                item -> list.contains(item.getId())
        ).collect(Collectors.toList());
    }

    public boolean isParent(String rootCategoryId, String categoryId) {
        if (rootCategoryId == null || rootCategoryId.equalsIgnoreCase(categoryId)) {
            return true;
        }
        if (categoryId == null) {
            return false;
        }
        return isParent(rootCategoryId, getParent(categoryId));
    }

    private String getParent(String categoryId) {
        Category category = getCategory(categoryId);
        if (category == null) {
            return null;
        }
        return category.getParent();
    }
}
