package com.github.jreddit.oauth.client;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.RedditPostRequest;

public abstract class RedditClient {
    
    
    public static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";
    
    
    public abstract String post(RedditToken rToken, RedditPostRequest request);
    
    
    public abstract String get(RedditToken rToken, RedditGetRequest request);
    
}
