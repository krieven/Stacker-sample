package sample.flow.catalog.states.category.contract;

public class CategoryA {
    private String categoryId;
    private Action action;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public enum Action {
        BACK, UP, OK
    }
}
