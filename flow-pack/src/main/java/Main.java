import io.github.krieven.stacker.flow.server.FlowServer;
import sample.flow.pack.flow.PackFlow;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(new PackFlow(), 4002).start();
    }
}