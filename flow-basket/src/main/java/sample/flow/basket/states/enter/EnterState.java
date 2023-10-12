package sample.flow.basket.states.enter;

import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.State;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.sample.flow.basket.flow.FlowData;

import javax.validation.constraints.NotNull;

public class EnterState extends State<FlowData, EnterState.Exits> {

    public EnterState() {
        super(Exits.values());
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends FlowData> flowContext) {
        return exitState(Exits.ADD, flowContext);
    }

    public enum Exits {
        ADD,
        SHOW
    }
}
