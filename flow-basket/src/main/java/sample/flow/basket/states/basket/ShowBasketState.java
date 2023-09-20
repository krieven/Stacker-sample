package sample.flow.basket.states.basket;

import com.google.common.collect.ImmutableMap;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import sample.contract.WrapQuestion;
import sample.flow.basket.states.basket.contract.ShowBasketA;
import sample.flow.basket.states.basket.contract.ShowBasketQ;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.function.BiFunction;

public class ShowBasketState extends StateQuestion<ShowBasketQ, ShowBasketA, ShowBasketStateData, ShowBasketState.Exits> {

    Map<ShowBasketA.Action, BiFunction<ShowBasketA, FlowContext<? extends ShowBasketStateData>, StateCompletion>> actionHandlers = ImmutableMap.of(
            ShowBasketA.Action.ADD, this::addActionHandler
    );

    public ShowBasketState() {
        super(new Contract<>(ShowBasketQ.class, ShowBasketA.class, new JsonParser()), new WrapQuestion<>(), Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(ShowBasketA showBasketA, FlowContext<? extends ShowBasketStateData> flowContext) {
        if (showBasketA == null) {
            return defaultActionHandler(null, flowContext);
        }

        return actionHandlers.getOrDefault(
                        showBasketA.getAction(),
                        this::defaultActionHandler)
                .apply(showBasketA, flowContext);
    }


    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends ShowBasketStateData> flowContext) {
        return defaultActionHandler(null, flowContext);
    }

    private StateCompletion addActionHandler(ShowBasketA answer, FlowContext<? extends ShowBasketStateData> context) {
        return exitState(Exits.PACK, context);
    }

    private StateCompletion defaultActionHandler(ShowBasketA showBasketA, FlowContext<? extends ShowBasketStateData> context) {
        return sendQuestion(new ShowBasketQ(), context);
    }

    public enum Exits {
        PACK,
//                EXECUTE,
//                EXIT,
        EDIT
    }
}
