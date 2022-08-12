package sample.flow.catalog.states.category;

import sample.flow.catalog.states.category.contract.CategoryA;


public interface CategoryData {

    CategoryA getCategoryA();

    void setCategoryA(CategoryA answer);

    String categoryGetRootCategoryId();

    String categoryGetCategoryId();

}
