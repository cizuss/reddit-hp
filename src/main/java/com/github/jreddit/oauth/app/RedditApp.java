package com.github.jreddit.oauth.app;

public abstract class RedditApp {
    
    private final String clientID;
    private final String clientSecret;
    private final String redirectURI;
    
    
    public RedditApp(String clientID, String clientSecret, String redirectURI) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
    }
    
    
    public String getClientID() {
        return clientID;
    }

    
    public String getClientSecret() {
        return clientSecret;
    }

    
    public String getRedirectURI() {
        return redirectURI;
    }
    
}
