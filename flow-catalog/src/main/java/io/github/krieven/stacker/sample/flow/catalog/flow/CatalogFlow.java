package io.github.krieven.stacker.sample.flow.catalog.flow;


import io.github.krieven.stacker.flow.BaseFlow;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.sample.flow.catalog.states.product.ProductState;
import io.github.krieven.stacker.util.Probe;
import io.github.krieven.stacker.sample.contract.WrapQuestion;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowContract;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRq;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRs;
import io.github.krieven.stacker.sample.flow.catalog.states.identification.IdentState;
import io.github.krieven.stacker.sample.flow.catalog.states.category.CategoryState;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.sample.model.Product;
import io.github.krieven.stacker.sample.services.CatalogCategoryService;
import io.github.krieven.stacker.sample.services.CatalogProductService;

import java.util.HashMap;
import java.util.Map;


public class CatalogFlow extends BaseFlow<CatalogFlowRq, CatalogFlowRs, FlowData> {

    private static final String CATEGORY = "category";
    private static final String PRODUCT = "product";
    private static final String IDENTIFICATION = "identification";
    private final CatalogCategoryService catalogCategoryService;
    private final CatalogProductService catalogProductService;

    public CatalogFlow(CatalogCategoryService catalogCategoryService, CatalogProductService catalogProductService) {
        super(
                new CatalogFlowContract(),
                FlowData.class,
                new JsonParser()
        );
        this.catalogCategoryService = catalogCategoryService;
        this.catalogProductService = catalogProductService;
    }

    protected void configure() {
        addState(
                CATEGORY,
                new CategoryState(catalogCategoryService, new WrapQuestion<>())
                        .withExit(CategoryState.Exits.FORWARD, PRODUCT)
                        .withTerminator(CategoryState.Exits.BACK)
        );
        addState(
                PRODUCT,
                new ProductState(catalogProductService, new WrapQuestion<>())
                        .withExit(ProductState.Exits.BACK, CATEGORY)
                        .withExit(ProductState.Exits.GET_DISCOUNT, IDENTIFICATION)
                        .withTerminator(ProductState.Exits.OK)
        );
        addState(IDENTIFICATION, new IdentState()
                .withExit(IdentState.Exits.RETURN, PRODUCT));

        setEnterState(CATEGORY);
    }

    protected boolean isDaemon(FlowContext<FlowData> flowContext) {
        return false;
    }

    protected FlowData createFlowData(CatalogFlowRq flowRequest, FlowContext<FlowData> flowContext) {
        return FlowData.build(flowRequest);
    }

    protected CatalogFlowRs makeReturn(FlowContext<FlowData> flowContext) {
        Product product = flowContext.getFlowData().getProductResult();

        if (product == null) {
            return null;
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
}
