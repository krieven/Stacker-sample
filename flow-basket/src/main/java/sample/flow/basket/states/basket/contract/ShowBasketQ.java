package sample.flow.basket.states.basket.contract;

import sample.flow.pack.contract.Pack;
import java.util.List;

public class ShowBasketQ {
    private List<Pack> packList;

    public List<Pack> getPackList() {
        return packList;
    }

    public void setPackList(List<Pack> packList) {
        this.packList = packList;
    }
}
