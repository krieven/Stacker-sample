package io.github.krieven.stacker.sample.flow.pack.flow;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.*;
import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogState;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.EditPackState;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRs;
import io.github.krieven.stacker.sample.flow.pack.states.enterPack.EnterPackState;
import io.github.krieven.stacker.sample.model.Pack;
import io.github.krieven.stacker.sample.services.CatalogCategoryService;
import io.github.krieven.stacker.util.Probe;

public class PackFlow extends BaseFlow<FlowPackRq, FlowPackRs, FlowPackData> {
    private static final String ENTER = "ENTER";
    private static final String CALL_CATALOG = "CALL_CATALOG";
    private static final String EDIT_PACK = "EDIT_PACK";
    private final CatalogCategoryService catalogService;

    public PackFlow(CatalogCategoryService catalogService) {
        super(
                new Contract<>(FlowPackRq.class, FlowPackRs.class, new JsonParser()),
                FlowPackData.class,
                new JsonParser()
        );
        this.catalogService = catalogService;
    }

    @Override
    protected void configure() {
        addState(ENTER, new EnterPackState()
                .withExit(EnterPackState.Exits.CREATE_PACK, CALL_CATALOG)
                .withExit(EnterPackState.Exits.EDIT_PACK, EDIT_PACK)
        );
        addState(CALL_CATALOG, new CallCatalogState()
                .withExit(CallCatalogState.Exits.RETURN_NULL, EDIT_PACK)
                .withExit(CallCatalogState.Exits.RETURN_PRODUCT, EDIT_PACK)
        );
        addState(EDIT_PACK, new EditPackState(catalogService)
                .withTerminator(EditPackState.Exits.OK)
                .withExit(EditPackState.Exits.ADD, CALL_CATALOG)
        );
        setEnterState(ENTER);
    }

    @Override
    protected boolean isDaemon(FlowContext<FlowPackData> flowContext) {
        return false;
    }

    @Override
    protected FlowPackData createFlowData(FlowPackRq flowPackRq, FlowContext<FlowPackData> flowContext) {
        FlowPackData flowData = new FlowPackData();
        flowData.setFlowPackRq(flowPackRq);
        return flowData;
    }

    @Override
    protected FlowPackRs makeReturn(FlowContext<FlowPackData> context) {
        FlowPackData flowData = context.getFlowData();
        FlowPackRs flowPackRs = new FlowPackRs();

        flowPackRs.setId(flowData.getId());
        flowPackRs.setPack(flowData.getThePack());

        return flowPackRs;
    }

}
