package sample.flow.pack.flow;

import sample.flow.pack.contract.FlowPackRq;
import sample.flow.pack.states.callCatalog.CallCataloqState;
import sample.flow.pack.states.callCatalog.CallCataloqStateData;
import sample.flow.pack.states.callCatalog.CallCatalogStateModel;
import sample.flow.pack.states.editPack.EditPackStateData;

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
    public CallCatalogStateModel getStateModel(CallCataloqState o) {
        if(getCallCatalogStateModel() == null){
            setCallCatalogStateModel(new CallCatalogStateModel());
            getCallCatalogStateModel().setCategoryId(getFlowPackRq().getCategoryId());
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
