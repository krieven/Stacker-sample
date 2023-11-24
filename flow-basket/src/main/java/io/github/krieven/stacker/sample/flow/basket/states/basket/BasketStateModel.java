package io.github.krieven.stacker.sample.flow.basket.states.basket;

import io.github.krieven.stacker.sample.model.Pack;

import java.util.List;

public interface BasketStateModel {
    void setIndex(Integer index);
    List<Pack> getPacks();
}
