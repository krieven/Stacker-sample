
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class Main {

    public static void main(String[] args) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        Resource resource = Resource.newResource(Main.class.getResource("/static"));
        resourceHandler.setBaseResource(resource);


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {
                Proxy.newHandler("/api", "http://localhost:8080"),
                resourceHandler
        });

        Server server = new Server(8000);

        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
