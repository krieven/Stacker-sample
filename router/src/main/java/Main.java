import com.fasterxml.jackson.databind.ObjectMapper;
import stacker.common.config.router.RouterConfig;
import stacker.router.server.HttpTransport;
import stacker.router.server.RouterServer;
import stacker.router.server.SimpleSessionStorage;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        RouterServer routerServer = new RouterServer(new HttpTransport(), new SimpleSessionStorage(), 8080);

        try {
            RouterConfig config = new ObjectMapper().readValue(Main.class.getResourceAsStream("config.json"), RouterConfig.class);
            routerServer.setConfig(config);
            routerServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
