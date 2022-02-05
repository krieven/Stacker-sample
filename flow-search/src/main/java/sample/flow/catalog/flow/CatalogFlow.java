package sample.flow.catalog.flow;


import org.jetbrains.annotations.NotNull;
import sample.flow.catalog.services.ProductCategoryService;
import sample.flow.catalog.states.category.CategoryExits;
import sample.flow.catalog.states.category.CategoryState;
import stacker.common.JsonParser;
import stacker.flow.*;


public class CatalogFlow extends BaseFlow<CatalogFlowRequest, CatalogFlowResponse, FlowData> {

    private static final String CATEGORY = "category";
    private static final String MODEL = "model";
    private static final String END = "end";

    public CatalogFlow() {
        super(
                new Contract<CatalogFlowRequest, CatalogFlowResponse>(
                        CatalogFlowRequest.class, CatalogFlowResponse.class, new JsonParser()
                ),
                FlowData.class,
                new JsonParser()
        );
    }

    protected void configure() {
        addState(END, new StateTerminator<>());
        addState(CATEGORY, new CategoryState(new ProductCategoryService())
                .withExit(CategoryExits.BACK, END)
        );

    }

    protected boolean isDaemon(FlowContext<FlowData> flowContext) {
        return false;
    }

    protected FlowData createFlowData(CatalogFlowRequest flowRequest, FlowContext<FlowData> flowContext) {
        return new FlowData();
    }

    protected CatalogFlowResponse makeReturn(FlowContext<FlowData> flowContext) {
        return null;
    }

    protected @NotNull StateCompletion onStart(FlowContext<FlowData> flowContext) {
        return enterState(CATEGORY, flowContext);
    }
}
