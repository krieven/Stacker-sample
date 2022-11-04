
import org.eclipse.jetty.proxy.AsyncProxyServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
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
                newHandler("/api", "http://localhost:7000/"),
                resourceHandler
        });

        Server server = new Server(8000);

        server.setHandler(handlers);

        server.start();
        server.join();
    }

    public static Handler newHandler(String contextPath, String targetUri){

        ServletHolder servletHolder = new ServletHolder(AsyncProxyServlet.Transparent.class);
        servletHolder.setAsyncSupported(true);
        servletHolder.setInitParameter("proxyTo", targetUri);
        servletHolder.setInitParameter("prefix", contextPath+"");

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath(contextPath+"/*");
        contextHandler.addServlet(servletHolder, "/*");

        return contextHandler;
    }
}
