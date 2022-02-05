package sample.flow.catalog.states.category;

import sample.flow.catalog.states.category.contract.CategoryAnswer;


public interface CategoryData {

    CategoryAnswer getCategoryAnswer();

    void setCategoryAnswer(CategoryAnswer answer);
}
