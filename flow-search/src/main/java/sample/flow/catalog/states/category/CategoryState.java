package sample.flow.catalog.states.category;

import org.jetbrains.annotations.NotNull;
import sample.flow.catalog.states.category.contract.CategoryAnswer;
import sample.flow.catalog.states.category.contract.CategoryQuestion;
import stacker.common.JsonParser;
import stacker.flow.Contract;
import stacker.flow.FlowContext;
import stacker.flow.StateCompletion;
import stacker.flow.StateQuestion;
import sample.flow.catalog.services.ProductCategoryService;

public class CategoryState extends StateQuestion<CategoryQuestion, CategoryAnswer, CategoryData, CategoryExits> {
    private final ProductCategoryService productCategoryService;

    public CategoryState(ProductCategoryService productCategoryService) {
        super(
                new Contract<>(CategoryQuestion.class, CategoryAnswer.class, new JsonParser()),
                CategoryExits.values()
        );
        this.productCategoryService = productCategoryService;
    }

    protected @NotNull StateCompletion handleAnswer(CategoryAnswer answer, FlowContext<? extends CategoryData> flowContext) {
        if ("OK".equals(answer.getAction())) {
            flowContext.getFlowData().setCategoryAnswer(answer);
            return exitState(CategoryExits.FORWARD, flowContext);
        }
        return exitState(CategoryExits.BACK, flowContext);
    }

    public @NotNull StateCompletion onEnter(FlowContext<? extends CategoryData> flowContext) {
        CategoryQuestion question = new CategoryQuestion();

        question.setFlow(flowContext.getFlowName());
        question.setState(flowContext.getStateName());
        question.setTitle("Please select product category");
        question.setCategories(productCategoryService.getProductCategoryesList());

        return sendQuestion(question, flowContext);
    }
}
