package io.github.krieven.stacker.sample.flow.basket.states.basket;

import javax.validation.constraints.NotNull;
public interface BasketStateData {
    @NotNull BasketStateModel getStateModel(BasketState state);
}
