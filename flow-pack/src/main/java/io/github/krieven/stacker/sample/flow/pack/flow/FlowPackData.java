package io.github.krieven.stacker.sample.flow.pack.flow;

import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogState;
import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCataloqStateData;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.EditPackStateData;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogStateModel;

public class FlowPackData implements CallCataloqStateData, EditPackStateData {

    private FlowPackRq flowPackRq;
    private CallCatalogStateModel callCatalogStateModel;

    public void setFlowRq(FlowPackRq flowPackRq) {
        this.flowPackRq = flowPackRq;
    }

    public FlowPackRq getFlowPackRq() {
        return flowPackRq;
    }

    @Override
    public CallCatalogStateModel getStateModel(CallCatalogState o) {
        if(getCallCatalogStateModel() == null){
            setCallCatalogStateModel(new CallCatalogStateModel());
        }
        return getCallCatalogStateModel();
    }

    public CallCatalogStateModel getCallCatalogStateModel() {
        return callCatalogStateModel;
    }

    public void setCallCatalogStateModel(CallCatalogStateModel callCatalogStateModel) {
        this.callCatalogStateModel = callCatalogStateModel;
    }
}
