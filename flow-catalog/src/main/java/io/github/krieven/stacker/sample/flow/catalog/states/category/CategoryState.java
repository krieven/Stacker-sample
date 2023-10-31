package io.github.krieven.stacker.sample.flow.catalog.states.category;

import com.google.common.collect.ImmutableMap;

import javax.validation.constraints.NotNull;

import io.github.krieven.stacker.sample.flow.catalog.states.category.contract.CategoryA;
import io.github.krieven.stacker.sample.flow.catalog.states.category.contract.CategoryQ;
import io.github.krieven.stacker.sample.model.Category;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import io.github.krieven.stacker.sample.services.CatalogCategoryService;
import io.github.krieven.stacker.util.Probe;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;


public class CategoryState extends StateQuestion<CategoryQ, CategoryA, CategoryData, CategoryState.Exits> {
    public static final String PLEASE_SELECT_PRODUCT_CATEGORY = "Please select product category";
    private final CatalogCategoryService catalogCategoryService;

    private final Map<CategoryA.Action,
            BiFunction<CategoryA, FlowContext<? extends CategoryData>, StateCompletion>
            > actionHandlers = ImmutableMap.of(

            CategoryA.Action.OK, this::onOkAction,

            CategoryA.Action.UP, this::onUpAction,

            CategoryA.Action.BACK, (q, f) -> exitState(Exits.BACK, f)
    );

    public CategoryState(@NotNull CatalogCategoryService catalogCategoryService, BiFunction<CategoryQ, FlowContext<?>, ?> wrapper) {
        super(
                new Contract<>(CategoryQ.class, CategoryA.class, new JsonParser()),
                wrapper,
                Exits.values()
        );

        this.catalogCategoryService = catalogCategoryService;
    }

    protected @NotNull StateCompletion handleAnswer(CategoryA answer, FlowContext<? extends CategoryData> context) {
        if (answer == null || answer.getAction() == null) {
            return onEnter(context);
        }

        return actionHandlers.get(answer.getAction()).apply(answer, context);
    }

    protected @NotNull StateCompletion onEnter(FlowContext<? extends CategoryData> context) {
        CategoryStateModel stateModel = context.getFlowData().getStateModel(this);
        if (stateModel.isEmpty()) {
            return exitState(Exits.BACK, context);
        }
        //if we are returned to this state from other state
        if (stateModel.getResultId() != null) {
            stateModel.pop();
            stateModel.setResultId(null);
            if (stateModel.isEmpty()) {
                return exitState(Exits.BACK, context);
            }
        }
        String categoryId = stateModel.peek();
        //if is leaf category then go forward
        if (catalogCategoryService.isProductCategory(categoryId)) {
            stateModel.setResultId(categoryId);
            return exitState(Exits.FORWARD, context);
        }
        if (categoryId != null && catalogCategoryService.getCategory(categoryId) == null) {
            return exitState(Exits.BACK, context);
        }

        return sendQuestion(createQ(categoryId), context);

    }

    private StateCompletion onOkAction(CategoryA answer, FlowContext<? extends CategoryData> context) {
        CategoryStateModel stateModel = context.getFlowData().getStateModel(this);
        String categoryId = answer.getCategoryId();
        stateModel.push(categoryId);
        if (catalogCategoryService.isProductCategory(categoryId)) {
            stateModel.setResultId(categoryId);
            return exitState(Exits.FORWARD, context);
        }
        return sendQuestion(createQ(categoryId), context);
    }

    private StateCompletion onUpAction(CategoryA answer, FlowContext<? extends CategoryData> context) {
        CategoryStateModel stateModel = context.getFlowData().getStateModel(this);
        if (!stateModel.isEmpty()) {
            stateModel.pop();
        }
        if (stateModel.isEmpty()) {
            return exitState(Exits.BACK, context);
        }
        String categoryId = stateModel.peek();
        if (catalogCategoryService.isProductCategory(categoryId)) {
            stateModel.setResultId(categoryId);
            return exitState(Exits.FORWARD, context);
        }
        return sendQuestion(createQ(categoryId), context);
    }

    private static String getTitle(Category category) {
        return Probe.tryGet(() -> "Please select category of " + category.getTitle())
                .orElse(PLEASE_SELECT_PRODUCT_CATEGORY);
    }

    private CategoryQ createQ(String categoryId) {
        CategoryQ question = new CategoryQ();
        question.setCategories(catalogCategoryService.getChildren(categoryId));
        question.setTitle(getTitle(catalogCategoryService.getCategory(categoryId)));
        return question;
    }

    public enum Exits {
        BACK,
        FORWARD
    }
}
