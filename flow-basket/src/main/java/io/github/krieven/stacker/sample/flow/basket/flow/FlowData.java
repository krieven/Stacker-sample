package io.github.krieven.stacker.sample.flow.basket.flow;

import io.github.krieven.stacker.sample.flow.basket.contract.BasketFlowRq;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketStateModel;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketState;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketStateData;
import io.github.krieven.stacker.sample.flow.basket.states.callPack.CallPackStateData;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRs;

import java.util.Optional;


public class FlowData implements CallPackStateData, BasketStateData {
    private BasketFlowRq basketFlowRq;
    private BasketStateModel basketStateModel;

    @Override
    public BasketStateModel getStateModel(BasketState state) {
        return basketStateModel = Optional.ofNullable(basketStateModel).orElse(new BasketStateModel());
    }

    public BasketFlowRq getBasketFlowRq() {
        return basketFlowRq;
    }

    public void setBasketFlowRq(BasketFlowRq basketFlowRq) {
        this.basketFlowRq = basketFlowRq;
    }

    public BasketStateModel getBasketStateModel() {
        return basketStateModel = basketStateModel == null ? new BasketStateModel(): basketStateModel;
    }

    public void setBasketStateModel(BasketStateModel basketStateModel) {
        this.basketStateModel = basketStateModel;
    }

    @Override
    public FlowPackRq createFlowPackRq() {
        FlowPackRq question = new FlowPackRq();
        question.setPack(getBasketStateModel().readSelectedPack());
        return question;
    }

    @Override
    public void setFlowPackRs(FlowPackRs flowPackRs) {
        if (flowPackRs == null) {
            return;
        }
        getBasketStateModel().putPack(flowPackRs.getPack());
    }

}
