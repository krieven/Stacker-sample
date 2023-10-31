package io.github.krieven.stacker.sample.services;

import io.github.krieven.stacker.sample.model.CategoryLink;
import io.github.krieven.stacker.sample.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CatalogCategoryServiceStub implements CatalogCategoryService {

    @Override
    public List<Category> getChildren(String parent) {
        return Stub.categories.stream().filter(
                item -> (parent == null && item.getParent() == null) || (parent != null && parent.equals(item.getParent()))
        ).collect(Collectors.toList());
    }

    @Override
    public Category getCategory(String categoryId) {
        return Stub.categories.stream().filter(
                item -> item.getId() != null && item.getId().equals(categoryId)
        ).findFirst().orElse(null);
    }

    @Override
    public List<Category> getAdditional(String categoryId) {
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

    @Override
    public boolean isProductCategory(String categoryId) {
        return getCategory(categoryId) != null && getChildren(categoryId).isEmpty();
    }

    @Override
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
