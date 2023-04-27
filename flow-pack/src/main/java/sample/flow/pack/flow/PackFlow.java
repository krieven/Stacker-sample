package sample.flow.pack.flow;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.*;
import org.jetbrains.annotations.NotNull;
import sample.contract.WrapQuestion;
import sample.flow.pack.contract.FlowPackRq;
import sample.flow.pack.contract.FlowPackRs;
import sample.flow.pack.states.callCatalog.CallCataloqState;
import sample.flow.pack.states.editPack.EditPackState;

public class PackFlow extends BaseFlow<FlowPackRq, FlowPackRs, FlowPackData> {
    private static final String CALL_CATALOG = "CALL_CATALOG";
    private static final String EDIT_PACK = "EDIT_PACK";
    private static final String TERMINATOR = "TERMINATOR";

    public PackFlow() {
        super(
                new Contract<>(FlowPackRq.class, FlowPackRs.class, new JsonParser()),
                FlowPackData.class,
                new JsonParser()
        );
    }

    @Override
    protected void configure() {
        addState(TERMINATOR, new StateTerminator<>());

        addState(CALL_CATALOG, new CallCataloqState()
                .withExit(CallCataloqState.Exits.RETURN, EDIT_PACK)
        );
        addState(EDIT_PACK, new EditPackState(new WrapQuestion<>())
                .withExit(EditPackState.Exits.OK, TERMINATOR)
                .withExit(EditPackState.Exits.ADD, CALL_CATALOG)
        );
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

    @Override
    protected @NotNull StateCompletion onStart(FlowContext<FlowPackData> flowContext) {
        System.out.println("hello ");
        return enterState(CALL_CATALOG, flowContext);
    }
}
