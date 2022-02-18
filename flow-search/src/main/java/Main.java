import io.github.krieven.stacker.flow.server.FlowServer;
import sample.flow.catalog.flow.CatalogFlow;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(new CatalogFlow(), 4001).start();
    }
}
