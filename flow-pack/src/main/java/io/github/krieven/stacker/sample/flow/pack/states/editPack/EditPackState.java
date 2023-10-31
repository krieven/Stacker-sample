package io.github.krieven.stacker.sample.flow.pack.states.editPack;

import com.google.common.collect.ImmutableMap;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;

import javax.validation.constraints.NotNull;

import io.github.krieven.stacker.sample.contract.WrapQuestion;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.contract.EditPackStateQ;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.contract.EditPackStateA;
import io.github.krieven.stacker.sample.model.Pack;
import io.github.krieven.stacker.sample.model.Product;
import io.github.krieven.stacker.sample.services.CatalogCategoryService;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import static io.github.krieven.stacker.sample.flow.pack.states.editPack.contract.EditPackStateA.Action;

public class EditPackState extends StateQuestion<EditPackStateQ, EditPackStateA, EditPackStateData, EditPackState.Exits> {
    private final CatalogCategoryService catalogService;

    private final Map<Action,
            BiFunction<
                    EditPackStateA,
                    FlowContext<? extends EditPackStateData>, StateCompletion>>
            actionHandlers = ImmutableMap.of(

            Action.ADD, this::onAddAction,

            Action.REM, this::onRemAction,

            Action.OK, this::onOkAction,

            Action.BACK, this::onBackAction
    );

    public EditPackState(CatalogCategoryService catalogService) {
        super(
                new Contract<>(EditPackStateQ.class, EditPackStateA.class, new JsonParser()),
                new WrapQuestion<>(),
                Exits.values());
        this.catalogService = catalogService;
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(EditPackStateA editPackStateA, FlowContext<? extends EditPackStateData> context) {
        if (editPackStateA == null || editPackStateA.getAction() == null) {
            Pack pack = context.getFlowData().getStateModel(this).getPack();
            if (pack == null || pack.isEmpty()) {
                return exitState(Exits.OK, context);
            }
            return sendQuestion(createQuestion(pack), context);
        }
        return actionHandlers.get(editPackStateA.getAction()).apply(editPackStateA, context);
    }

    @Override
    public @NotNull StateCompletion onEnter(FlowContext<? extends EditPackStateData> context) {
        Pack pack = context.getFlowData().getStateModel(this).getPack();
        if (pack == null || pack.isEmpty()) {
            return exitState(Exits.OK, context);
        }
        return sendQuestion(createQuestion(pack), context);
    }

    private EditPackStateQ createQuestion(Pack pack) {

        EditPackStateQ question = new EditPackStateQ();

        pack.getProducts().stream()
                .filter(Objects::nonNull)
                .forEach(product -> {
                    question.addItem(product);
                    catalogService.getAdditional(product.getCategory()).stream()
                            .filter(Objects::nonNull)
                            .forEach(question::addItem);
                });

        return question;
    }

    /**
     * action handlers
     */
    private StateCompletion onAddAction(EditPackStateA answer, FlowContext<? extends EditPackStateData> context){
        context.getFlowData().getStateModel(this).setAddProductCategory(answer.getId());
        return exitState(Exits.ADD, context);
    }

    private StateCompletion onRemAction(EditPackStateA answer, FlowContext<? extends EditPackStateData> context){
        Pack pack = context.getFlowData().getStateModel(this).getPack();

        Product product = pack.getProducts().stream()
                .filter(p ->
                        p.getId().equals(answer.getId()))
                .findFirst()
                .orElse(null);
        pack.getProducts().remove(product);

        if (pack.isEmpty()) {
            return exitState(Exits.OK, context);
        }
        return sendQuestion(createQuestion(pack), context);
    }

    private StateCompletion onOkAction(EditPackStateA editPackStateA, FlowContext<? extends EditPackStateData> context) {
        return exitState(Exits.OK, context);
    }

    private StateCompletion onBackAction(EditPackStateA editPackStateA, FlowContext<? extends EditPackStateData> context) {
        context.getFlowData().getStateModel(this).removeAll();
        return exitState(Exits.OK, context);
    }

    /**
     * 
     */
    public enum Exits {
        ADD, OK
    }
}
