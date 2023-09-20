package io.github.krieven.stacker.sample.flow.catalog.states.category;

public class CategoryStateModel {
    private String rootCategoryId;
    private String currentCategoryId;
    private String productCategoryId;

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

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
}
