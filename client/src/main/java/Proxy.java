import org.eclipse.jetty.proxy.AsyncProxyServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServletRequest;

public class Proxy extends AsyncProxyServlet {

    private final String targetUri;

    Proxy(String targetUri) {
        this.targetUri = targetUri;
    }

    @Override
    protected String rewriteTarget(HttpServletRequest request) {
        return targetUri;
    }

    public static Handler newHandler(String contextPath, String targetUri){
        ServletHolder servletHolder = new ServletHolder(new Proxy(targetUri));
        servletHolder.setAsyncSupported(true);

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath(contextPath);
        contextHandler.addServlet(servletHolder, "");

        return contextHandler;
    }
}
