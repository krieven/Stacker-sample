package sample.flow.basket.flow;

import sample.flow.basket.states.basket.BasketStateModel;
import sample.flow.basket.states.basket.ShowBasketState;
import sample.flow.basket.states.basket.ShowBasketStateData;
import sample.flow.basket.states.callPack.CallPackState;
import sample.flow.basket.states.callPack.CallPackStateData;
import sample.flow.basket.states.callPack.CallPackStateModel;

import java.util.Optional;


public class FlowData implements CallPackStateData, ShowBasketStateData {
    private BasketFlowRq basketFlowRq;
    private CallPackStateModel callPackStateModel;
    private BasketStateModel basketStateModel;

    @Override
    public CallPackStateModel getStateModel(CallPackState o) {
        return callPackStateModel = Optional.ofNullable(callPackStateModel)
                .orElse(new CallPackStateModel());
    }

    @Override
    public BasketStateModel getStateModel(ShowBasketState state) {
        return basketStateModel = Optional.ofNullable(basketStateModel).orElse(new BasketStateModel());
    }

    public BasketFlowRq getBasketFlowRq() {
        return basketFlowRq;
    }

    public void setBasketFlowRq(BasketFlowRq basketFlowRq) {
        this.basketFlowRq = basketFlowRq;
    }


    public BasketStateModel getBasketStateModel() {
        return basketStateModel;
    }

    public void setBasketStateModel(BasketStateModel basketStateModel) {
        this.basketStateModel = basketStateModel;
    }

    public CallPackStateModel getCallPackStateModel() {
        return callPackStateModel;
    }

    public void setCallPackStateModel(CallPackStateModel callPackStateModel) {
        this.callPackStateModel = callPackStateModel;
    }
}
