package io.github.krieven.stacker.sample.flow.pack.states.enterPack;


import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.State;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.sample.flow.pack.flow.FlowPackData;

import javax.validation.constraints.NotNull;

/**
 * Entering state for the flow-pack
 */
public class EnterPackState extends State<FlowPackData, EnterPackState.Exits> {

    /**
     * Constructs State with exits declaration
     */
    public EnterPackState() {
        super(Exits.values());
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends FlowPackData> context) {
        if (context.getFlowData().getThePack() == null) {
            return exitState(Exits.CREATE_PACK, context);
        }
        return exitState(Exits.EDIT_PACK, context);
    }

    public enum Exits {
        CREATE_PACK,
        EDIT_PACK
    }
}
