package io.github.krieven.stacker.sample.services;

import io.github.krieven.stacker.sample.model.Category;

import java.util.List;

public interface CatalogCategoryService {
    List<Category> getChildren(String parent);

    Category getCategory(String categoryId);

    List<Category> getAdditional(String categoryId);

    boolean isParent(String rootCategoryId, String categoryId);
}
