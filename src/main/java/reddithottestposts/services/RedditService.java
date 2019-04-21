package reddithottestposts.services;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditScriptApp;
import com.github.jreddit.oauth.client.RedditClient;
import com.github.jreddit.oauth.client.RedditHttpClient;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.exception.RedditParseException;
import com.github.jreddit.parser.listing.SubmissionsListingParser;
import com.github.jreddit.request.retrieval.param.SubmissionSort;
import com.github.jreddit.request.retrieval.submissions.SubmissionsOfSubredditRequest;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reddithottestposts.entities.UserRedditPost;
import reddithottestposts.repos.UserRedditPostRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class RedditService {
    @Autowired
    private UserRedditPostRepository userRedditPostRepository;

    private final Config config = ConfigFactory.load().getConfig("reddit");
    private final String userAgent = "jReddit: Reddit API Wrapper for Java";
    private final String clientID = config.getString("client-id");
    private final String redirectURI = config.getString("redirect-uri");
    private final String secret = config.getString("secret");

    private class AppContext {
        public RedditClient client;
        public RedditToken token;

        public AppContext(RedditClient client, RedditToken token) {
            this.client = client;
            this.token = token;
        }
    }

    private AppContext setupCtx() throws RedditOAuthException {
//        // Information about the app
//        String userAgent = "jReddit: Reddit API Wrapper for Java";
//        String clientID = "FuQv1qpqmusTRw";
//        String redirectURI = "http://www.google.com";
//        String secret = "H9M1zJz9ek3YXksw0z7SEshcEBA";

        RedditApp redditApp = new RedditScriptApp(clientID, secret, redirectURI);
        RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);
        RedditClient client = new RedditHttpClient(userAgent, HttpClientBuilder.create().build());

        // Create a application-only token (will be valid for 1 hour)
        RedditToken token = agent.tokenAppOnly(false);

        return new AppContext(client, token);
    }

    public Submission getRandomPostFromSubreddits(List<String> subreddits, String email) throws RedditOAuthException, RedditParseException {
        Collections.shuffle(subreddits);

        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, -2);
        Date date2DaysAgo = cal.getTime();

        Iterable<UserRedditPost> posts = userRedditPostRepository.findByEmailWithCreatedAtAfter(email, date2DaysAgo);
        Stream<UserRedditPost> stream = StreamSupport.stream(posts.spliterator(), false);
        Set<String> postUrls = stream.map(p -> p.getPostUrl()).collect(Collectors.toSet());
        return recurseGetRandomPostFromSubreddits(subreddits, postUrls);
    }

    private Submission recurseGetRandomPostFromSubreddits(List<String> subreddits, Set<String> userPostUrls) throws RedditOAuthException, RedditParseException {
        if (subreddits.isEmpty()) {
            return null; // shouldn't really reach this case ever
        }

        String subreddit = subreddits.get(0);
        List<Submission> submissions = getHotPostsBySubreddit(subreddit, 20).stream().filter(p -> !userPostUrls.contains(p.getURL())).collect(Collectors.toList());

        if (submissions.isEmpty()) {
            return recurseGetRandomPostFromSubreddits(subreddits.subList(1, subreddits.size() - 1), userPostUrls);
        }

        Collections.shuffle(submissions);

        return submissions.get(0);
    }

    public List<Submission> getHotPostsBySubreddit(String subreddit, int limit) throws RedditOAuthException, RedditParseException {
        AppContext ctx = setupCtx();
// Create parser for request
        SubmissionsListingParser parser = new SubmissionsListingParser();

// Create the request
        SubmissionsOfSubredditRequest request = (SubmissionsOfSubredditRequest) new SubmissionsOfSubredditRequest(subreddit, SubmissionSort.HOT).setLimit(limit);

// Perform and parse request, and store parsed result
        List<Submission> submissions = parser.parse(ctx.client.get(ctx.token, request));
        return submissions;
    }
}
