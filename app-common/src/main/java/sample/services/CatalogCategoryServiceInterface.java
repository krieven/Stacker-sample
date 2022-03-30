package sample.services;

import sample.model.Category;

import java.util.List;

public interface CatalogCategoryServiceInterface {
    List<Category> getCategories(String parent);

    Category getCategory(String categoryId);

    List<Category> getAdditionals(String categoryId);

    boolean isParent(String rootCategoryId, String categoryId);
}
