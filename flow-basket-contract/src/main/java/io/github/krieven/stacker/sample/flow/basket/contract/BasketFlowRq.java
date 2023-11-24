package io.github.krieven.stacker.sample.flow.basket.contract;

import io.github.krieven.stacker.sample.model.Pack;

import java.util.List;

public class BasketFlowRq {
    private List<Pack> packs;

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }
}
