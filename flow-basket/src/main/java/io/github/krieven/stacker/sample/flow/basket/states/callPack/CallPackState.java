package io.github.krieven.stacker.sample.flow.basket.states.callPack;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateOuterCall;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRs;

import javax.validation.constraints.NotNull;

public class CallPackState extends StateOuterCall<FlowPackRq, FlowPackRs, CallPackStateData, CallPackState.Exits> {


    public CallPackState() {
        super(new Contract<>(FlowPackRq.class, FlowPackRs.class, new JsonParser()), Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(FlowPackRs flowPackRs, FlowContext<? extends CallPackStateData> context) {
        context.getFlowData().setFlowPackRs(flowPackRs);
        return exitState(Exits.OK, context);
    }

    @Override
    protected @NotNull StateCompletion onErrorParsingAnswer(FlowContext<? extends CallPackStateData> context) {
        return exitState(Exits.OK, context);
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends CallPackStateData> context) {
        return sendQuestion(context.getFlowData().createFlowPackRq(), context);
    }

    public enum Exits{
        OK
    }
}
