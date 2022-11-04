package sample.flow.catalog.states.category;

public class CategoryStateModel {
    private String rootCategoryId;
    private String currentCategoryId;

    public String getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(String rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public String getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(String currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
    }
}
