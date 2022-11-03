package sample.flow.catalog.flow;

import org.jetbrains.annotations.NotNull;
import sample.flow.catalog.states.category.CategoryData;
import sample.flow.catalog.states.category.CategoryState;
import sample.flow.catalog.states.category.CategoryStateModel;
import sample.flow.catalog.states.product.ProductData;
import sample.flow.catalog.states.product.ProductState;
import sample.flow.catalog.states.product.ProductStateModel;

import java.util.Optional;

public class FlowData implements CategoryData, ProductData {
    private CatalogFlowRq flowRequest;
    private CategoryStateModel categoryStatModel;
    private ProductStateModel productStateModel;

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
            getCategoryStateModel().setCurrentCategoryId(getFlowRequest().getCategoryId());
        }
        return getCategoryStateModel();
    }

    @Override
    @NotNull
    public ProductStateModel getStateModel(ProductState productState) {
        if (getProductStateModel() == null) {
            setProductStateModel(new ProductStateModel());
            getProductStateModel().setCategoryId(getCategoryStateModel() == null ? null : getCategoryStateModel().getCurrentCategoryId());
        }
        return getProductStateModel();
    }

    public CategoryStateModel getCategoryStateModel() {
        return categoryStatModel;
    }

    public void setCategoryStateModel(CategoryStateModel categoryStatModel) {
        this.categoryStatModel = categoryStatModel;
    }

    public ProductStateModel getProductStateModel() {
        return productStateModel;
    }

    public void setProductStateModel(ProductStateModel productStateModel) {
        this.productStateModel = productStateModel;
    }
}
