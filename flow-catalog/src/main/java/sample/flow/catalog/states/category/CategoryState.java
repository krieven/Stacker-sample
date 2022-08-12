package sample.flow.catalog.states.category;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import sample.model.Category;
import sample.flow.catalog.states.category.contract.CategoryA;
import sample.flow.catalog.states.category.contract.CategoryQ;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import sample.services.CatalogCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;


public class CategoryState extends StateQuestion<CategoryQ, CategoryA, CategoryData, CategoryState.Exits> {
    private final CatalogCategoryService catalogCategoryService;

    private final Map<
                CategoryA.Action,
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
        answer = answer == null ? flowContext.getFlowData().getCategoryA() : answer;

        if (answer == null) {
            return onEnter(flowContext);
        }

        return Optional.ofNullable(actionHandlers.get(answer.getAction()))
                .orElse(actionHandlers.get(CategoryA.Action.OK))
                .apply(answer, flowContext);
    }

    public @NotNull StateCompletion onEnter(FlowContext<? extends CategoryData> flowContext) {

        String categoryId = flowContext.getFlowData().categoryGetCategoryId();
        String rootCategoryId = flowContext.getFlowData().categoryGetRootCategoryId();

        //if categoryId is not categoryId or if we are trying to go upper than the Flow argument root
        //then exit to back
        if ((categoryId != null && catalogCategoryService.getCategory(categoryId) == null) ||
                !catalogCategoryService.isParent(rootCategoryId, categoryId)) {
            return exitState(Exits.BACK, flowContext);
        }
        //if chosen category has no children categories then go forward
        if (catalogCategoryService.getChildren(categoryId).isEmpty()) {
            CategoryA answer = new CategoryA();
            answer.setCategoryId(categoryId);
            flowContext.getFlowData().setCategoryA(answer);

            return exitState(Exits.FORWARD, flowContext);
        }
        //otherwise, send question
        CategoryQ question = new CategoryQ();

        question.setTitle("Please select product category");

        question.setCategories(
                catalogCategoryService.getChildren(
                        categoryId
                )
        );

        return sendQuestion(question, flowContext);
    }

    private StateCompletion onOkAction(CategoryA answer, FlowContext<? extends CategoryData> flowContext) {
        if(answer.getCategoryId() == null){
            return onEnter(flowContext);
        }
        Category category = catalogCategoryService.getCategory(answer.getCategoryId());

        if (category == null) {
            return onEnter(flowContext);
        }

        flowContext.getFlowData().setCategoryA(answer);

        List<Category> categories = catalogCategoryService.getChildren(category.getId());

        if (categories.isEmpty()) {
            return exitState(Exits.FORWARD, flowContext);
        }

        CategoryQ question = new CategoryQ();

        question.setTitle("Please select category of " + category.getTitle());
        question.setCategories(categories);

        return sendQuestion(question, flowContext);
    }

    private StateCompletion onUpAction(CategoryA answer, FlowContext<? extends CategoryData> flowContext) {
        String id = flowContext.getFlowData().getCategoryA() == null ? null :
                flowContext.getFlowData().getCategoryA().getCategoryId();

        id = id == null ? flowContext.getFlowData().categoryGetRootCategoryId() : id;

        Category current = catalogCategoryService.getCategory(id);
        String parent = current == null ? null : current.getParent();

        CategoryA up = new CategoryA();
        up.setCategoryId(parent);
        up.setAction(CategoryA.Action.OK);
        return onOkAction(up, flowContext);
    }

    public enum Exits {
        BACK,
        FORWARD
    }
}
