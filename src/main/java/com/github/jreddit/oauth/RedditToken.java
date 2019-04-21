package com.github.jreddit.oauth;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;

import com.github.jreddit.oauth.param.RedditScope;
import com.github.jreddit.oauth.param.RedditTokenCompleteScope;


public class RedditToken {

    
    public static final String PARAM_TOKEN_TYPE = "token_type";
    
    
    private String accessToken;
    
    
    private String refreshToken;
    
    
    private RedditTokenCompleteScope scopes;
    
    
    private String tokenType;
    
    
    private long expiration;
    
    
    private long expirationSpan;
    
    
    protected RedditToken(OAuthJSONAccessTokenResponse token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
        this.expiration = currentTimeSeconds() + token.getExpiresIn();
        this.expirationSpan = token.getExpiresIn();
        this.scopes = new RedditTokenCompleteScope(token.getScope());
        this.tokenType = token.getParam(PARAM_TOKEN_TYPE);
    }
    
    
    protected RedditToken(String accessToken, String tokenType, long expiresIn, String scope) {
        this.accessToken = accessToken;
        this.refreshToken = null;
        this.expiration = currentTimeSeconds() + expiresIn;
        this.expirationSpan = expiresIn;
        this.scopes = new RedditTokenCompleteScope(scope);
        this.tokenType = tokenType;
    }
    
    
    public void refresh(OAuthJSONAccessTokenResponse token) {
        this.accessToken = token.getAccessToken();
        this.expiration = currentTimeSeconds() + token.getExpiresIn();
        this.expirationSpan = token.getExpiresIn();
        this.scopes = new RedditTokenCompleteScope(token.getScope());
        this.tokenType = token.getParam(PARAM_TOKEN_TYPE);
    }

    
    public String getAccessToken() {
        return accessToken;
    }
    
    
    public String getRefreshToken() {
        return refreshToken;
    }

    
    public boolean isExpired() {
        return expiration < currentTimeSeconds();
    }
    
    
    public boolean hasScope(RedditScope scope) {
        return this.scopes.has(scope);
    }
    
    
    public long getExpiration() {
        return expiration;
    }    
    
    public String getTokenType() {
        return tokenType;
    }
    
    
    public boolean isRefreshable() {
        return this.getRefreshToken() != null;
    }
    
    
    public boolean willExpireIn(long seconds) {
        return currentTimeSeconds() + seconds > expiration;
    }
    
    
    public long getExpirationSpan() {
        return expirationSpan;
    }
    
    
    private static long currentTimeSeconds() {
        return System.currentTimeMillis() / (long) 1000;
    }
    
}
