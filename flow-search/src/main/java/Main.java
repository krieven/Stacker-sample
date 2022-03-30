import io.github.krieven.stacker.flow.server.FlowServer;
import sample.flow.catalog.flow.CatalogFlow;
import sample.services.CatalogCategoryServiceStub;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(new CatalogFlow(new CatalogCategoryServiceStub()), 4001).start();
    }
}
