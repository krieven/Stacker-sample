package sample.flow.catalog.states.product;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateQuestion;
import org.jetbrains.annotations.NotNull;
import sample.contract.WrapQuestion;
import sample.model.Product;
import sample.services.CatalogProductService;
import sample.flow.catalog.states.product.contract.ProductA;
import sample.flow.catalog.states.product.contract.ProductQ;

import java.util.List;
import java.util.function.BiFunction;

public class ProductState extends StateQuestion<ProductQ, ProductA, ProductData, ProductState.Exits> {
    private final CatalogProductService productService;

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
        return null;
    }

    @Override
    public @NotNull StateCompletion onEnter(FlowContext<? extends ProductData> flowContext) {
        String categoryId = flowContext.getFlowData().takeProductCategoryId();
        if(categoryId == null){
            return exitState(Exits.NO_CATEGORY, flowContext);
        }

        List<Product> byCategory = productService.getByCategory(categoryId);

        ProductQ question = new ProductQ();

        question.setProducts(byCategory);
        question.setFieldNames(productService.getFieldNamesByCategory(categoryId));

        return sendQuestion(question, flowContext);
    }

    public enum Exits {
        NO_CATEGORY,
        PRODUCT_CHOOSEN,
        GET_DISCOUNT
    }
}
