package io.github.krieven.stacker.sample.flow.catalog.states.identification;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateOuterCall;
import javax.validation.constraints.NotNull;

import io.github.krieven.stacker.sample.flow.catalog.states.identification.contract.IdentA;
import io.github.krieven.stacker.sample.flow.catalog.states.identification.contract.IdentQ;

public class IdentState extends StateOuterCall<IdentQ, IdentA, IdentData, IdentState.Exits> {


    public IdentState() {
        super(new Contract<>(IdentQ.class, IdentA.class, new JsonParser()), Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(IdentA answer, FlowContext<? extends IdentData> context) {
        return exitState(Exits.RETURN, context);
    }

    @Override
    protected @NotNull StateCompletion onErrorParsingAnswer(FlowContext<? extends IdentData> context) {
        return exitState(Exits.RETURN, context);
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends IdentData> context) {
        return exitState(Exits.RETURN, context);
    }

    public enum Exits{
        RETURN
    }
}
