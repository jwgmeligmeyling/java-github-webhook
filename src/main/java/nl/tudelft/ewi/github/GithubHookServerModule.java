package nl.tudelft.ewi.github;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;

import com.google.inject.AbstractModule;

import lombok.extern.slf4j.Slf4j;
import nl.tudelft.ewi.github.jaxrs.models.PullRequestEvent;

import org.jboss.resteasy.plugins.guice.ext.JaxrsModule;
import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import org.reflections.Reflections;

@Slf4j
public class GithubHookServerModule extends AbstractModule {
	
	@Override
	protected void configure() {
		install(new RequestScopeModule());
		install(new JaxrsModule());
		
		bind(GithubHook.class).toInstance(new GithubHook() {
			
			@Override
			public void onPullRequest(PullRequestEvent event) {
				log.info("Received payload : {}", event);
			}
			
		});
		
		findResourcesWith(Path.class);
		findResourcesWith(Provider.class);
	}

	private void findResourcesWith(Class<? extends Annotation> ann) {
		Reflections reflections = new Reflections(getClass().getPackage().getName());
		for (Class<?> clasz : reflections.getTypesAnnotatedWith(ann)) {
			log.info("Registering resource {}", clasz);
			bind(clasz);
		}
	}

}
