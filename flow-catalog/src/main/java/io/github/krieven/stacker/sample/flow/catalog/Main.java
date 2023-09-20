package io.github.krieven.stacker.sample.flow.catalog;

import io.github.krieven.stacker.flow.server.FlowServer;
import io.github.krieven.stacker.sample.flow.catalog.flow.CatalogFlow;
import io.github.krieven.stacker.sample.services.CatalogCategoryServiceStub;
import io.github.krieven.stacker.sample.services.CatalogProductServiceStub;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(
                new CatalogFlow(new CatalogCategoryServiceStub(), new CatalogProductServiceStub()),
                4001
        ).start();
    }
}
