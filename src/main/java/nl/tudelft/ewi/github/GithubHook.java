package nl.tudelft.ewi.github;

import nl.tudelft.ewi.github.jaxrs.models.PullRequestEvent;

public interface GithubHook {

	void onPullRequest(PullRequestEvent event) throws Exception;
	
}
