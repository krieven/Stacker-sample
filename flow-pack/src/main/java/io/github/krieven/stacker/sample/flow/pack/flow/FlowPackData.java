package io.github.krieven.stacker.sample.flow.pack.flow;

import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogState;
import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogStateData;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.EditPackStateData;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.model.Product;

public class FlowPackData implements CallCatalogStateData, EditPackStateData {

    private FlowPackRq flowPackRq;

    public void setFlowRq(FlowPackRq flowPackRq) {
        this.flowPackRq = flowPackRq;
    }

    public FlowPackRq getFlowPackRq() {
        return flowPackRq;
    }



    @Override
    public CallCatalogStateModel getStateModel(CallCatalogState o) {
        return new CallCatalogStateModel() {
            @Override
            public String getCategoryId() {
                return null;
            }

            @Override
            public void setProduct(Product product) {

            }
        };
    }
}
