package sample.flow.catalog.flow;

import sample.flow.catalog.states.category.CategoryData;
import sample.flow.catalog.states.category.contract.CategoryA;
import sample.flow.catalog.states.product.ProductData;

import java.util.Optional;

public class FlowData implements CategoryData, ProductData {
    private CatalogFlowRq flowRequest;
    private CategoryA categoryAnswer;

    public static FlowData build(CatalogFlowRq flowRequest) {
        FlowData result = new FlowData();
        result.setFlowRequest(flowRequest);
        return result;
    }

    public CategoryA getCategoryA() {
        return getCategoryAnswer();
    }

    public void setCategoryA(CategoryA answer) {
        setCategoryAnswer(answer);
    }

    @Override
    public String takeRootCategoryId() {
        return Optional.ofNullable(getFlowRequest()).orElseGet(CatalogFlowRq::new).getCategoryId();
    }

    @Override
    public String takeProductCategoryId() {
        return Optional.ofNullable(getCategoryAnswer()).orElseGet(CategoryA::new).getCategoryId();
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
}
