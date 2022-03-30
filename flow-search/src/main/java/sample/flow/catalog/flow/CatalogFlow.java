package sample.flow.catalog.flow;


import io.github.krieven.stacker.flow.BaseFlow;
import io.github.krieven.stacker.flow.Contract;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.flow.StateTerminator;
import org.jetbrains.annotations.NotNull;
import sample.contract.WrapQuestion;
import sample.flow.catalog.states.product.ProductState;
import sample.flow.catalog.states.category.CategoryState;
import io.github.krieven.stacker.common.JsonParser;
import sample.services.CatalogCategoryServiceInterface;
import sample.services.CatalogProductService;


public class CatalogFlow extends BaseFlow<CatalogFlowRq, CatalogFlowRs, FlowData> {

    private static final String CATEGORY = "category";
    private static final String PRODUCT = "product";
    private static final String END = "end";
    private static final String IDENTIFICATION = "identification";
    private final CatalogCategoryServiceInterface catalogCategoryService;

    public CatalogFlow(CatalogCategoryServiceInterface catalogCategoryService) {
        super(
                new Contract<>(
                        CatalogFlowRq.class, CatalogFlowRs.class, new JsonParser()
                ),
                FlowData.class,
                new JsonParser()
        );
        this.catalogCategoryService = catalogCategoryService;
    }

    protected void configure() {
        addState(END, new StateTerminator<>());
        addState(
                CATEGORY,
                new CategoryState(catalogCategoryService, new WrapQuestion<>())
                        .withExit(CategoryState.Exits.BACK, END)
                        .withExit(CategoryState.Exits.FORWARD, PRODUCT)
        );
        addState(
                PRODUCT,
                new ProductState(new CatalogProductService(), new WrapQuestion<>())
                        .withExit(ProductState.Exits.NO_CATEGORY, END)
                        .withExit(ProductState.Exits.PRODUCT_CHOOSEN, END)
                        .withExit(ProductState.Exits.GET_DISCOUNT, IDENTIFICATION)
        );
    }

    protected boolean isDaemon(FlowContext<FlowData> flowContext) {
        return false;
    }

    protected FlowData createFlowData(CatalogFlowRq flowRequest, FlowContext<FlowData> flowContext) {
        return FlowData.build(flowRequest);
    }

    protected CatalogFlowRs makeReturn(FlowContext<FlowData> flowContext) {
        return new CatalogFlowRs();
    }

    protected @NotNull StateCompletion onStart(FlowContext<FlowData> flowContext) {
        return enterState(CATEGORY, flowContext);
    }
}
