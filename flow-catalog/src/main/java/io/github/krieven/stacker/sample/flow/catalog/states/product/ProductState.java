package io.github.krieven.stacker.sample.flow.catalog.states.product;

import com.google.common.collect.ImmutableMap;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import io.github.krieven.stacker.sample.flow.catalog.states.product.contract.ProductA;
import io.github.krieven.stacker.sample.flow.catalog.states.product.contract.ProductFilter;
import io.github.krieven.stacker.sample.flow.catalog.states.product.contract.ProductQ;
import io.github.krieven.stacker.sample.flow.catalog.states.product.productHandler.ProductResourceHandler;
import io.github.krieven.stacker.sample.model.Category;
import io.github.krieven.stacker.sample.services.CatalogCategoryService;
import io.github.krieven.stacker.sample.services.CatalogProductService;
import io.github.krieven.stacker.util.ListUtils;

import javax.validation.constraints.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class ProductState extends StateQuestion<ProductQ, ProductA, ProductData, ProductState.Exits> {
    private final CatalogProductService productService;
    private final CatalogCategoryService categoryService;
    private static final String  FILTER_PATH = "/products";

    private final Map<ProductA.Action, BiFunction<ProductA, FlowContext<? extends ProductData>, StateCompletion>>
            actionHandlers = ImmutableMap.of(
            ProductA.Action.OK, this::onOkAction,
            ProductA.Action.BACK, this::onBackAction);

    public ProductState(CatalogCategoryService categoryService, CatalogProductService productService, BiFunction<ProductQ, FlowContext<?>, ?> wrapper) {
        super(
                new Contract<>(ProductQ.class, ProductA.class, new JsonParser()),
                wrapper,
                Exits.values()
        );
        this.categoryService = categoryService;
        this.productService = productService;

        defineResourceController(FILTER_PATH, new ProductResourceHandler(this, productService));
    }

    @Override
    protected @NotNull StateCompletion handleAnswer(ProductA productA, FlowContext<? extends ProductData> flowContext) {
        if (productA == null || productA.getAction() == null) {
            return onEnter(flowContext);
        }

        return Optional.ofNullable(actionHandlers.get(productA.getAction()))
                .orElse(actionHandlers.get(ProductA.Action.OK))
                .apply(productA, flowContext);
    }

    @Override
    protected @NotNull StateCompletion onEnter(FlowContext<? extends ProductData> flowContext) {
        String categoryId = flowContext.getFlowData().getStateModel(this).getCategoryId();
        Category category = categoryService.getCategory(categoryId);
        if (categoryId == null) {
            return exitState(Exits.BACK, flowContext);
        }

        ProductQ question = new ProductQ();

        question.setFieldNames(productService.getFieldNamesByCategory(categoryId));
        question.setCategoryName(category.getTitle());
        question.setProductFilters(
                ListUtils.aListOf(
                        new ProductFilter(FILTER_PATH, "filter", null)
                )
        );

        return sendQuestion(question, flowContext);
    }

    private @NotNull StateCompletion onBackAction(ProductA productA, FlowContext<? extends ProductData> flowContext) {
        return exitState(Exits.BACK, flowContext);
    }

    private @NotNull StateCompletion onOkAction(ProductA productA, FlowContext<? extends ProductData> flowContext) {
        flowContext.getFlowData().getStateModel(this).setProduct(productService.getById(productA.getId()));
        return exitState(Exits.OK, flowContext);
    }

    public enum Exits {
        BACK,
        OK,
        GET_DISCOUNT
    }
}
