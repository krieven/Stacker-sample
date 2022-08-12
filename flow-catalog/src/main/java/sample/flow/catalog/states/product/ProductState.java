package sample.flow.catalog.states.product;

import com.google.common.collect.ImmutableMap;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import org.jetbrains.annotations.NotNull;
import sample.model.Product;
import sample.services.CatalogProductService;
import sample.flow.catalog.states.product.contract.ProductA;
import sample.flow.catalog.states.product.contract.ProductQ;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class ProductState extends StateQuestion<ProductQ, ProductA, ProductData, ProductState.Exits> {
    private final CatalogProductService productService;

    private final Map<ProductA.Action, BiFunction<ProductA, FlowContext<? extends ProductData>, StateCompletion>>
            actionHandlers = ImmutableMap.of(
            ProductA.Action.OK, this::onOkAction,
            ProductA.Action.BACK, this::onBackAction
    );

    private @NotNull StateCompletion onBackAction(ProductA productA, FlowContext<? extends ProductData> flowContext) {
        return exitState(Exits.BACK, flowContext);
    }

    private @NotNull StateCompletion onOkAction(ProductA productA, FlowContext<? extends ProductData> flowContext) {
        flowContext.getFlowData().setProduct(productService.getById(productA.getId()));
        return exitState(Exits.OK, flowContext);
    }

    public ProductState(CatalogProductService productService, BiFunction<ProductQ, FlowContext<?>, ?> wrapper) {
        super(
                new Contract<>(ProductQ.class, ProductA.class, new JsonParser()),
                wrapper,
                Exits.values()
        );
        this.productService = productService;
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
    public @NotNull StateCompletion onEnter(FlowContext<? extends ProductData> flowContext) {
        String categoryId = flowContext.getFlowData().takeProductCategoryId();
        if (categoryId == null) {
            return exitState(Exits.BACK, flowContext);
        }

        List<Product> byCategory = productService.getByCategory(categoryId);

        ProductQ question = new ProductQ();

        question.setProducts(byCategory);
        question.setFieldNames(productService.getFieldNamesByCategory(categoryId));

        return sendQuestion(question, flowContext);
    }

    public enum Exits {
        BACK,
        OK,
        GET_DISCOUNT
    }
}
