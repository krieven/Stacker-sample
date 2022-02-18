package sample.model;

public class CategoryLink {
    private String category;
    private String addCategory;

    public static CategoryLink build(String category, String addCategory) {
        CategoryLink link = new CategoryLink();
        link.category = category;
        link.addCategory = addCategory;
        return link;
    }

    public String getCategory() {
        return category;
    }

    public String getAddCategory() {
        return addCategory;
    }
}
