package sample.flow.pack.states.callCatalog;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.*;
import org.jetbrains.annotations.NotNull;
import sample.flow.catalog.contract.CatalogFlowRq;
import sample.flow.catalog.contract.CatalogFlowRs;

public class CallCataloqState extends StateOuterCall<CatalogFlowRq, CatalogFlowRs, CallCataloqStateData, CallCataloqState.Exits> {
    public CallCataloqState() {
        super(new Contract<>(CatalogFlowRq.class, CatalogFlowRs.class, new JsonParser()), Exits.values());
    }

    @Override
    public @NotNull StateCompletion onEnter(FlowContext<? extends CallCataloqStateData> flowContext) {
        CallCatalogStateModel stateModel = flowContext.getFlowData().getStateModel(this);
        return sendQuestion(new CatalogFlowRq(), flowContext);
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(CatalogFlowRs catalogFlowRs, FlowContext<? extends CallCataloqStateData> flowContext) {
        return null;
    }

    @Override
    protected @NotNull StateCompletion onErrorParsingAnswer(FlowContext<? extends CallCataloqStateData> flowContext) {
        return exitState(Exits.RETURN, flowContext);
    }

    public enum Exits {
        RETURN
    }
}
