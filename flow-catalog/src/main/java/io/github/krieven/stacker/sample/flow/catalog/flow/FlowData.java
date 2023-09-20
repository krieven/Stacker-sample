package io.github.krieven.stacker.sample.flow.catalog.flow;

import javax.validation.constraints.NotNull;

import io.github.krieven.stacker.sample.flow.catalog.states.identification.IdentStateModel;
import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRq;
import io.github.krieven.stacker.sample.flow.catalog.states.category.CategoryData;
import io.github.krieven.stacker.sample.flow.catalog.states.category.CategoryState;
import io.github.krieven.stacker.sample.flow.catalog.states.category.CategoryStateModel;
import io.github.krieven.stacker.sample.flow.catalog.states.identification.IdentData;
import io.github.krieven.stacker.sample.flow.catalog.states.identification.IdentState;
import io.github.krieven.stacker.sample.flow.catalog.states.product.ProductData;
import io.github.krieven.stacker.sample.flow.catalog.states.product.ProductState;
import io.github.krieven.stacker.sample.model.Product;

import java.util.Optional;

public class FlowData implements CategoryData, ProductData, IdentData {
    private CatalogFlowRq flowRequest;
    private CategoryStateModel categoryStatModel;
    private IdentStateModel identStateModel;
    private Product productResult;

    public static @NotNull FlowData build(CatalogFlowRq flowRequest) {
        FlowData result = new FlowData();
        result.setFlowRequest(flowRequest);
        return result;
    }

    private void setFlowRequest(CatalogFlowRq flowRequest) {
        this.flowRequest = flowRequest;
    }

    public CatalogFlowRq getFlowRequest() {
        return flowRequest;
    }

    @Override
    @NotNull
    public CategoryStateModel getStateModel(CategoryState state) {
        flowRequest = Optional.ofNullable(flowRequest).orElse(new CatalogFlowRq());
        if (getCategoryStateModel() == null) {
            setCategoryStateModel(new CategoryStateModel());
            getCategoryStateModel().setRootCategoryId(getFlowRequest().getCategoryId());
        }
        return getCategoryStateModel();
    }

    @Override
    @NotNull
    public ProductData.StateModel getStateModel(ProductState productState) {
        return new ProductDataStateModel();
    }

    @Override
    public IdentStateModel getStateModel(IdentState state) {
        return Optional.ofNullable(this.identStateModel)
                .orElse(this.identStateModel = new IdentStateModel());
    }

    //POJO section

    public CategoryStateModel getCategoryStateModel() {
        return categoryStatModel;
    }

    public void setCategoryStateModel(CategoryStateModel categoryStatModel) {
        this.categoryStatModel = categoryStatModel;
    }

    public IdentStateModel getIdentStateModel() {
        return identStateModel;
    }

    public void setIdentStateModel(IdentStateModel identStateModel) {
        this.identStateModel = identStateModel;
    }

    public Product getProductResult() {
        return productResult;
    }

    public void setProductResult(Product productResult) {
        this.productResult = productResult;
    }


    private class ProductDataStateModel implements ProductData.StateModel {

        @Override
        public String getCategoryId() {
            return getCategoryStateModel().getProductCategoryId();
        }

        @Override
        public void setProduct(Product product) {
            productResult = product;
        }
    }
}
