package io.github.krieven.stacker.sample.flow.pack;

import io.github.krieven.stacker.flow.server.FlowServer;
import io.github.krieven.stacker.sample.flow.pack.flow.PackFlow;
import io.github.krieven.stacker.sample.services.CatalogCategoryServiceStub;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(new PackFlow(new CatalogCategoryServiceStub()), 4002).start();
    }
}