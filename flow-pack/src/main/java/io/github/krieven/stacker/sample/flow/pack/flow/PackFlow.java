package io.github.krieven.stacker.sample.flow.pack.flow;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.*;
import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogState;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.EditPackState;
import io.github.krieven.stacker.sample.contract.WrapQuestion;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRs;

public class PackFlow extends BaseFlow<FlowPackRq, FlowPackRs, FlowPackData> {
    private static final String CALL_CATALOG = "CALL_CATALOG";
    private static final String EDIT_PACK = "EDIT_PACK";

    public PackFlow() {
        super(
                new Contract<>(FlowPackRq.class, FlowPackRs.class, new JsonParser()),
                FlowPackData.class,
                new JsonParser()
        );
    }

    @Override
    protected void configure() {

        addState(CALL_CATALOG, new CallCatalogState()
                .withExit(CallCatalogState.Exits.RETURN, EDIT_PACK)
        );
        addState(EDIT_PACK, new EditPackState(new WrapQuestion<>())
                .withTerminator(EditPackState.Exits.OK)
                .withExit(EditPackState.Exits.ADD, CALL_CATALOG)
        );
        setEnterState(CALL_CATALOG);
    }

    @Override
    protected boolean isDaemon(FlowContext<FlowPackData> flowContext) {
        return false;
    }

    @Override
    protected FlowPackData createFlowData(FlowPackRq flowPackRq, FlowContext<FlowPackData> flowContext) {
        FlowPackData flowData = new FlowPackData();
        flowData.setFlowRq(flowPackRq);
        return flowData;
    }

    @Override
    protected FlowPackRs makeReturn(FlowContext<FlowPackData> flowContext) {
        return null;
    }

}
