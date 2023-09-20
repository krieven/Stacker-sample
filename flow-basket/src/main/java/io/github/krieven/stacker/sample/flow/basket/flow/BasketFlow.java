package io.github.krieven.stacker.sample.flow.basket.flow;

import io.github.krieven.stacker.sample.flow.basket.contract.BasketFlowRq;
import io.github.krieven.stacker.sample.flow.basket.contract.BasketFlowRs;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.*;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketState;
import io.github.krieven.stacker.sample.flow.basket.contract.BasketFlowContract;
import io.github.krieven.stacker.sample.flow.basket.states.callPack.CallPackState;
import io.github.krieven.stacker.sample.flow.basket.states.enter.EnterState;

import javax.validation.constraints.NotNull;

public class BasketFlow extends BaseFlow<BasketFlowRq, BasketFlowRs, FlowData> {

    private static final String PACK = "PACK";
    private static final String BASKET = "BASKET";
    private static final String ENTER = "ENTER";

    public BasketFlow() {
        super(new BasketFlowContract(), FlowData.class, new JsonParser());
    }

    @Override
    protected void configure() {

        addState(ENTER, new EnterState()
                .withExit(EnterState.Exits.ADD, PACK)
                .withExit(EnterState.Exits.SHOW, BASKET)
        );

        addState(PACK, new CallPackState().withExit(CallPackState.Exits.OK, BASKET));

        addState(BASKET, new BasketState()
                        .withExit(BasketState.Exits.PACK, PACK)
//                .withExit(ShowBasketState.Exits.EXECUTE, EXECUTE)
//                        .withTerminator(ShowBasketState.Exits.EXIT)
                        .withExit(BasketState.Exits.EDIT, PACK)
        );

        setEnterState(ENTER);
    }

    @Override
    protected boolean isDaemon(FlowContext<FlowData> flowContext) {
        return false;
    }

    @Override
    protected FlowData createFlowData(BasketFlowRq basketFlowRq, FlowContext<FlowData> flowContext) {
        FlowData flowData = new FlowData();
        flowData.setBasketFlowRq(basketFlowRq);
        return flowData;
    }

    @Override
    protected BasketFlowRs makeReturn(FlowContext<FlowData> flowContext) {
        return null;
    }

    protected @NotNull StateCompletion onStart(FlowContext<FlowData> flowContext) {
        return this.enterState(PACK, flowContext);
    }
}
