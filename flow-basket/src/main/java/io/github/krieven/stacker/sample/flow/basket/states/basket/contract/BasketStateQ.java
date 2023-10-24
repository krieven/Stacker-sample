package io.github.krieven.stacker.sample.flow.basket.states.basket.contract;

import io.github.krieven.stacker.sample.model.Pack;

import java.util.List;

public class BasketStateQ {
    private List<Pack> packList;

    public List<Pack> getPackList() {
        return packList;
    }

    public void setPackList(List<Pack> packList) {
        this.packList = packList;
    }
}
