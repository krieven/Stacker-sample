package sample.flow.basket.states.callPack;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateOuterCall;
import sample.flow.pack.contract.FlowPackRq;
import sample.flow.pack.contract.FlowPackRs;

import javax.validation.constraints.NotNull;

public class CallPackState extends StateOuterCall<FlowPackRq, FlowPackRs, CallPackStateData, CallPackState.Exits> {


    public CallPackState() {
        super(new Contract<>(FlowPackRq.class, FlowPackRs.class, new JsonParser()), Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(FlowPackRs flowPackRs, FlowContext<? extends CallPackStateData> flowContext) {

        return exitState(Exits.OK, flowContext);
    }

    @Override
    protected @NotNull StateCompletion onErrorParsingAnswer(FlowContext<? extends CallPackStateData> flowContext) {
        return exitState(Exits.OK, flowContext);
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends CallPackStateData> flowContext) {
        CallPackStateModel stateModel = flowContext.getFlowData().getStateModel(this);

        return sendQuestion(stateModel.createQuestion(), flowContext);
    }

    public enum Exits{
        OK
    }
}
