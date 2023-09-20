package io.github.krieven.stacker.sample.flow.basket.contract;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;

public class BasketFlowContract extends Contract<BasketFlowRq, BasketFlowRs> {
    public BasketFlowContract() {
        super(BasketFlowRq.class, BasketFlowRs.class, new JsonParser());
    }
}
