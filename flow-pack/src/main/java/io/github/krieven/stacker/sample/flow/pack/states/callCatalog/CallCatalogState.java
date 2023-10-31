package io.github.krieven.stacker.sample.flow.pack.states.callCatalog;

import io.github.krieven.stacker.flow.*;

import javax.validation.constraints.NotNull;

import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowContract;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRq;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRs;

public class CallCatalogState extends StateOuterCall<CatalogFlowRq, CatalogFlowRs, CallCatalogStateData, CallCatalogState.Exits> {
    public CallCatalogState() {
        super(new CatalogFlowContract(), Exits.values());
    }

    @Override
    public @NotNull StateCompletion onEnter(FlowContext<? extends CallCatalogStateData> flowContext) {
        CallCatalogStateData.CallCatalogStateModel stateModel = flowContext.getFlowData().getStateModel(this);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId(stateModel.getCategoryId());
        return sendQuestion(rq, flowContext);
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(CatalogFlowRs catalogFlowRs, FlowContext<? extends CallCatalogStateData> flowContext) {
        if (catalogFlowRs == null || catalogFlowRs.getProduct() == null) {
            return exitState(Exits.RETURN_NULL, flowContext);
        }
        CallCatalogStateData.CallCatalogStateModel stateModel = flowContext.getFlowData().getStateModel(this);
        stateModel.addProduct(catalogFlowRs.getProduct());
        return exitState(Exits.RETURN_PRODUCT, flowContext);
    }

    @Override
    protected @NotNull StateCompletion onErrorParsingAnswer(FlowContext<? extends CallCatalogStateData> flowContext) {
        return exitState(Exits.RETURN_NULL, flowContext);
    }

    public enum Exits {
        RETURN_NULL,
        RETURN_PRODUCT
    }
}
