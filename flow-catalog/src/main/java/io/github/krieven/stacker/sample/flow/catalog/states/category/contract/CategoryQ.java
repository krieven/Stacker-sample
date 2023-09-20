package io.github.krieven.stacker.sample.flow.catalog.states.category.contract;

import io.github.krieven.stacker.sample.model.Category;

import java.util.List;

public class CategoryQ {

    private String title;

    private List<Category> categories;

    private CategoryA.Action[] actions = CategoryA.Action.values();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public CategoryA.Action[] getActions() {
        return actions;
    }

    public void setActions(CategoryA.Action[] actions) {
        this.actions = actions;
    }
}
