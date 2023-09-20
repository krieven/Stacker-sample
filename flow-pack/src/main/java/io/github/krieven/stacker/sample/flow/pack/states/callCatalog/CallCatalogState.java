package io.github.krieven.stacker.sample.flow.pack.states.callCatalog;

import io.github.krieven.stacker.flow.*;
import javax.validation.constraints.NotNull;import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowContract;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRq;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRs;

public class CallCatalogState extends StateOuterCall<CatalogFlowRq, CatalogFlowRs, CallCataloqStateData, CallCatalogState.Exits> {
    public CallCatalogState() {
        super(new CatalogFlowContract(), Exits.values());
    }

    @Override
    public @NotNull StateCompletion onEnter(FlowContext<? extends CallCataloqStateData> flowContext) {
        CallCatalogStateModel stateModel = flowContext.getFlowData().getStateModel(this);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId(stateModel.getCategoryId());
        return sendQuestion(rq, flowContext);
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(CatalogFlowRs catalogFlowRs, FlowContext<? extends CallCataloqStateData> flowContext) {
        if(catalogFlowRs == null){
            return exitState(Exits.RETURN, flowContext);
        }
        CallCatalogStateModel stateModel = flowContext.getFlowData().getStateModel(this);
        stateModel.setCategoryId(catalogFlowRs.getId());
        return exitState(Exits.RETURN, flowContext);
    }

    @Override
    protected @NotNull StateCompletion onErrorParsingAnswer(FlowContext<? extends CallCataloqStateData> flowContext) {
        return exitState(Exits.RETURN, flowContext);
    }

    public enum Exits {
        RETURN
    }
}
