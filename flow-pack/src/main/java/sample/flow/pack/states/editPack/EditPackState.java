package sample.flow.pack.states.editPack;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import javax.validation.constraints.NotNull;import sample.flow.pack.states.editPack.contract.EditPackStateA;
import sample.flow.pack.states.editPack.contract.EditPackStateQ;

import java.util.function.BiFunction;


public class EditPackState extends StateQuestion<EditPackStateQ, EditPackStateA,EditPackStateData,EditPackState.Exits> {
    public EditPackState(BiFunction<EditPackStateQ, FlowContext<?>, ?> wrapper) {
        super(
                new Contract<>(EditPackStateQ.class, EditPackStateA.class, new JsonParser()),
                wrapper,
                Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(EditPackStateA editPackStateA, FlowContext<? extends EditPackStateData> flowContext) {
        return null;
    }

    @Override
    public @NotNull StateCompletion onEnter(FlowContext<? extends EditPackStateData> flowContext) {
        return exitState(Exits.OK, flowContext);
    }

    public enum Exits {
        ADD,
        OK
    }
}
