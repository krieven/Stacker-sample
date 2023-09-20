package io.github.krieven.stacker.sample.flow.catalog.states.product.productHandler;

import io.github.krieven.stacker.sample.model.Product;

import java.util.List;

public class Response {
    private List<Product> products;
    private int page;
    private int pageSize;
    private int listSize;

    public static Response create(int pageNumber, int pageSize, int listSize, List<Product> products) {
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
