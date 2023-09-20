package io.github.krieven.stacker.sample.flow.basket.states.basket;

import com.google.common.collect.ImmutableMap;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import io.github.krieven.stacker.sample.contract.WrapQuestion;
import io.github.krieven.stacker.sample.flow.basket.states.basket.contract.BasketStateA;
import io.github.krieven.stacker.sample.flow.basket.states.basket.contract.BasketStateAction;
import io.github.krieven.stacker.sample.flow.basket.states.basket.contract.BasketStateQ;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.function.BiFunction;

public class BasketState extends StateQuestion<BasketStateQ, BasketStateA, BasketStateData, BasketState.Exits> {

    Map<BasketStateAction, BiFunction<BasketStateA, FlowContext<? extends BasketStateData>, StateCompletion>> actionHandlers = ImmutableMap.of(
            BasketStateAction.ADD, this::addActionHandler,
            BasketStateAction.EDIT, this::editActionHandler
    );

    public BasketState() {
        super(new Contract<>(BasketStateQ.class, BasketStateA.class, new JsonParser()), new WrapQuestion<>(), Exits.values());
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(BasketStateA showBasketA, FlowContext<? extends BasketStateData> flowContext) {
        if (showBasketA == null) {
            return defaultActionHandler(null, flowContext);
        }

        return actionHandlers.getOrDefault(
                        showBasketA.getAction(),
                        this::defaultActionHandler)
                .apply(showBasketA, flowContext);
    }


    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends BasketStateData> flowContext) {
        return defaultActionHandler(null, flowContext);
    }

    private StateCompletion addActionHandler(BasketStateA answer, FlowContext<? extends BasketStateData> context) {
        return exitState(Exits.PACK, context);
    }
    private StateCompletion editActionHandler(BasketStateA basketStateA, FlowContext<? extends BasketStateData> flowContext) {
        BasketStateModel stateModel = flowContext.getFlowData().getStateModel(this);
        stateModel.setIndex(basketStateA.getIndex());
        return exitState(Exits.PACK, flowContext);
    }

    private StateCompletion defaultActionHandler(BasketStateA basketStateA, FlowContext<? extends BasketStateData> context) {
        return sendQuestion(new BasketStateQ(), context);
    }

    public enum Exits {
        PACK,
//                EXECUTE,
//                EXIT,
        EDIT
    }
}
