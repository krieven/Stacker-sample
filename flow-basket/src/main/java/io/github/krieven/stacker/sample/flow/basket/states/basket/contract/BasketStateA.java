package io.github.krieven.stacker.sample.flow.basket.states.basket.contract;

public class BasketStateA {

    private Action action;
    private Integer index;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public enum Action {
        ADD,
        EDIT,
        DELETE,
        OK
    }
}
