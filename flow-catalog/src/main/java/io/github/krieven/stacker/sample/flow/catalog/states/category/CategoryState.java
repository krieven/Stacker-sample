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

    protected @NotNull StateCompletion handleAnswer(CategoryA answer, FlowContext<? extends CategoryData> flowContext) {
        if (answer == null) {
            return onEnter(flowContext);
        }

        return Optional.ofNullable(actionHandlers.get(answer.getAction()))
                .orElse(actionHandlers.get(CategoryA.Action.OK))
                .apply(answer, flowContext);
    }

    protected @NotNull StateCompletion onEnter(FlowContext<? extends CategoryData> flowContext) {

        CategoryStateModel categoryStateModel = flowContext.getFlowData().getStateModel(this);

        String categoryId = categoryStateModel.getCurrentCategoryId();
        String rootCategoryId = categoryStateModel.getRootCategoryId();

        //if categoryId is not categoryId or if we are trying to go up over the Flow argument root
        //then exit to back
        Category category = catalogCategoryService.getCategory(categoryId);
        if ((categoryId != null && category == null) ||
                !catalogCategoryService.isParent(rootCategoryId, categoryId)) {
            return exitState(Exits.BACK, flowContext);
        }
        //if chosen category has no children categories then go forward
        if (catalogCategoryService.getChildren(categoryId).isEmpty()) {
            categoryStateModel.setProductCategoryId(categoryId);
            return exitState(Exits.FORWARD, flowContext);
        }
        //otherwise, send question
        CategoryQ question = new CategoryQ();

        question.setTitle(getTitle(category));

        question.setCategories(
                catalogCategoryService.getChildren(
                        categoryId
                )
        );

        return sendQuestion(question, flowContext);
    }

    private StateCompletion onOkAction(CategoryA answer, FlowContext<? extends CategoryData> flowContext) {

        Category category = catalogCategoryService.getCategory(answer.getCategoryId());
        CategoryStateModel stateModel = flowContext.getFlowData().getStateModel(this);

        if (!catalogCategoryService.isParent(stateModel.getRootCategoryId(), answer.getCategoryId())) {
            return onEnter(flowContext);
        }

        List<Category> children = catalogCategoryService.getChildren(answer.getCategoryId());

        if (children.isEmpty()) {
            stateModel.setProductCategoryId(answer.getCategoryId());
            return exitState(Exits.FORWARD, flowContext);
        }

        stateModel.setCurrentCategoryId(answer.getCategoryId());

        CategoryQ question = new CategoryQ();
        question.setTitle(
                getTitle(category)
        );
        question.setCategories(children);

        return sendQuestion(question, flowContext);
    }

    private StateCompletion onUpAction(CategoryA answer, FlowContext<? extends CategoryData> flowContext) {
        CategoryStateModel model = flowContext.getFlowData().getStateModel(this);
        String id = model.getCurrentCategoryId();
        id = id == null ? model.getRootCategoryId() : id;

        Category current = catalogCategoryService.getCategory(id);
        if(current == null) {
            return exitState(Exits.BACK, flowContext);
        }
        String parent = current.getParent();

        CategoryA up = new CategoryA();
        up.setCategoryId(parent);
        up.setAction(CategoryA.Action.OK);
        return onOkAction(up, flowContext);
    }

    private static String getTitle(Category category) {
        return Probe.tryGet(() -> "Please select category of " + category.getTitle())
                .orElse(PLEASE_SELECT_PRODUCT_CATEGORY);
    }

    public enum Exits {
        BACK,
        FORWARD
    }
}
