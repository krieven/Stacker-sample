package sample.flow.catalog.flow;

import sample.flow.catalog.states.category.CategoryData;
import sample.flow.catalog.states.category.contract.CategoryA;
import sample.flow.catalog.states.product.ProductData;

import java.util.Optional;

public class FlowData implements CategoryData, ProductData {
    private CatalogFlowRq flowRequest;
    private CategoryA categoryAnswer;
    private String parentCategoryId;

    public static FlowData build(CatalogFlowRq flowRequest) {
        FlowData result = new FlowData();
        result.setFlowRequest(flowRequest);
        return result;
    }

    public CategoryA getCategoryA() {
        return getCategoryAnswer();
    }

    public void setCategoryA(CategoryA answer) {
        setParentCategoryId(null);
        setCategoryAnswer(answer);
    }

    @Override
    public String categoryGetRootCategoryId() {
        return Optional.ofNullable(getFlowRequest()).orElseGet(CatalogFlowRq::new).getCategoryId();
    }

    @Override
    public String categoryGetCategoryId() {
        String id = getCategoryA() == null ? getParentCategoryId() : getCategoryA().getCategoryId();
        return id == null ? categoryGetRootCategoryId() : id;
    }

    @Override
    public String takeProductCategoryId() {
        return getCategoryAnswer() == null ? null : getCategoryA().getCategoryId();
    }

    @Override
    public void setProductParentCategoryId(String parentId) {
        setCategoryA(null);
        this.setParentCategoryId(parentId);
    }


    public CatalogFlowRq getFlowRequest() {
        return flowRequest;
    }

    public void setFlowRequest(CatalogFlowRq flowRequest) {
        this.flowRequest = flowRequest;
    }

    public CategoryA getCategoryAnswer() {
        return categoryAnswer;
    }

    public void setCategoryAnswer(CategoryA categoryAnswer) {
        this.categoryAnswer = categoryAnswer;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
