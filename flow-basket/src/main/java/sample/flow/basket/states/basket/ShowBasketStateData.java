package sample.flow.basket.states.basket;

import javax.validation.constraints.NotNull;
public interface ShowBasketStateData {
    @NotNull BasketStateModel getStateModel(ShowBasketState state);
}
