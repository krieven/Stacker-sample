package io.github.krieven.stacker.sample.model;

public class CategoryLink {
    private String category;
    private String addCategory;

    public static CategoryLink build(String category, String addCategory) {
        CategoryLink link = new CategoryLink();
        link.setCategory(category);
        link.setAddCategory(addCategory);
        return link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddCategory() {
        return addCategory;
    }

    public void setAddCategory(String addCategory) {
        this.addCategory = addCategory;
    }
}
