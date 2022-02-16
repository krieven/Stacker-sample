import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.krieven.stacker.common.config.router.RouterConfig;
import io.github.krieven.stacker.router.server.HttpTransport;
import io.github.krieven.stacker.router.server.RouterServer;
import io.github.krieven.stacker.router.server.SimpleSessionStorage;

public class Main {

    public static void main(String[] args) throws Exception {

        RouterServer routerServer = new RouterServer(new HttpTransport(), new SimpleSessionStorage(), 8080);

        RouterConfig config = new ObjectMapper().readValue(Main.class.getResourceAsStream("config.json"), RouterConfig.class);

        if(routerServer.setConfig(config)){
            routerServer.start();
        }

    }
}
