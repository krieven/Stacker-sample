package sample.flow.basket.states.basket;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import org.jetbrains.annotations.NotNull;
import sample.contract.WrapQuestion;
import sample.flow.basket.states.basket.contract.ShowBasketA;
import sample.flow.basket.states.basket.contract.ShowBasketQ;

public class ShowBasketState extends StateQuestion<ShowBasketQ, ShowBasketA, ShowBasketStateData, ShowBasketState.Exits> {
    public ShowBasketState() {
        super(new Contract<>(ShowBasketQ.class, ShowBasketA.class, new JsonParser()), new WrapQuestion<>(), Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(ShowBasketA showBasketA, FlowContext<? extends ShowBasketStateData> flowContext) {
        return null;
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends ShowBasketStateData> flowContext) {
        return null;
    }


    public enum Exits{
        PACK,
//        EXECUTE,
        EXIT
    }
}
