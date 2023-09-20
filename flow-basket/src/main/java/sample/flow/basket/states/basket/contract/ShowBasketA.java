package sample.flow.basket.states.basket.contract;

public class ShowBasketA {

    private Action action;
    private int index = -1;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public enum Action {
        ADD,
        EDIT,
        DELETE,
        OK
    }
}
