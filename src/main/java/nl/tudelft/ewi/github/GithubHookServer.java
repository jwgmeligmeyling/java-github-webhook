package nl.tudelft.ewi.github;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import lombok.extern.slf4j.Slf4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.common.collect.ImmutableList;
import com.google.inject.Module;

@Slf4j
public class GithubHookServer {
	
	public static void main(String[] args) throws Exception {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		GithubHookServer server = new GithubHookServer();
		server.startServer();
	}

	private final Server server;

	public GithubHookServer() throws IOException {
		log.info("Starting build-server on port: {}", 8080);
		this.server = new Server(8080);
		server.setHandler(new GithubHookServerHandler());
	}
	
	public void startServer() throws Exception {
		server.start();
		server.join();
	}
	
	public void stopServer() throws Exception {
		server.stop();
	}
	
	private static class GithubHookServerHandler extends ServletContextHandler {
		
		public GithubHookServerHandler() {
			addEventListener(new GuiceResteasyBootstrapServletContextListener() {
				@Override
				protected List<Module> getModules(ServletContext context) {
					return ImmutableList.<Module>of(new GithubHookServerModule());
				}
			});
			
			addServlet(HttpServletDispatcher.class, "/");
		}
	}
	
}
