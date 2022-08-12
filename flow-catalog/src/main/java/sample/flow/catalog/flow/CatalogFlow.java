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
import sample.model.Product;
import sample.services.CatalogCategoryService;
import sample.services.CatalogProductService;

import java.util.HashMap;
import java.util.Map;


public class CatalogFlow extends BaseFlow<CatalogFlowRq, CatalogFlowRs, FlowData> {

    private static final String CATEGORY = "category";
    private static final String PRODUCT = "product";
    private static final String END = "end";
    private static final String IDENTIFICATION = "identification";
    private final CatalogCategoryService catalogCategoryService;
    private final CatalogProductService catalogProductService;

    public CatalogFlow(CatalogCategoryService catalogCategoryService, CatalogProductService catalogProductService) {
        super(
                new Contract<>(
                        CatalogFlowRq.class, CatalogFlowRs.class, new JsonParser()
                ),
                FlowData.class,
                new JsonParser()
        );
        this.catalogCategoryService = catalogCategoryService;
        this.catalogProductService = catalogProductService;
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
                new ProductState(catalogProductService, new WrapQuestion<>())
                        .withExit(ProductState.Exits.BACK, CATEGORY)
                        .withExit(ProductState.Exits.OK, END)
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
        Product product = flowContext.getFlowData().getProduct();

        if(product==null){
            return new CatalogFlowRs();
        }

        CatalogFlowRs catalogFlowRs = new CatalogFlowRs();
        catalogFlowRs.setId(product.getId());
        catalogFlowRs.setCategory(product.getCategory());
        catalogFlowRs.setName(product.getName());
        catalogFlowRs.setPrice(product.getPrice());
        Map<String, CatalogFlowRs.Field> fields = new HashMap<>();
        product.getFields().forEach((key, value) -> {
            CatalogFlowRs.Field field = new CatalogFlowRs.Field();
            field.setType(value.getType().name());
            field.setValue(value.getValue());
            fields.put(key, field);
        });
        catalogFlowRs.setFields(fields);
        return catalogFlowRs;
    }

    protected @NotNull StateCompletion onStart(FlowContext<FlowData> flowContext) {
        return enterState(CATEGORY, flowContext);
    }
}
