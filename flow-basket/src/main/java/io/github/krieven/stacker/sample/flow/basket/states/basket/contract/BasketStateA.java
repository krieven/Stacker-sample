package io.github.krieven.stacker.sample.flow.basket.states.basket.contract;

public class BasketStateA {

    private BasketStateAction action;
    private int index = -1;

    public BasketStateAction getAction() {
        return action;
    }

    public void setAction(BasketStateAction action) {
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
