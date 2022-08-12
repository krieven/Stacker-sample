package sample.flow.catalog.states.product.contract;

public class ProductA {
    private Action action;
    private String id;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum Action {
        OK, BACK
    }
}
