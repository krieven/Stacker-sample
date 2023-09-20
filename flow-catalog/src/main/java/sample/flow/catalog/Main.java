package sample.flow.catalog;

import io.github.krieven.stacker.flow.server.FlowServer;
import sample.flow.catalog.flow.CatalogFlow;
import sample.services.CatalogCategoryServiceStub;
import sample.services.CatalogProductServiceStub;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(
                new CatalogFlow(new CatalogCategoryServiceStub(), new CatalogProductServiceStub()),
                4001
        ).start();
    }
}
