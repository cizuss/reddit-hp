package com.github.jreddit.oauth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.oauth.param.RedditDuration;
import com.github.jreddit.oauth.param.RedditScopeBuilder;
import com.github.jreddit.request.util.KeyValueFormatter;

import javax.xml.bind.DatatypeConverter;


public class RedditOAuthAgent {
    
    
    private static final String REDDIT_AUTHORIZE = "https://www.reddit.com/api/v1/authorize?";
    
    
    private static final String GRANT_TYPE_INSTALLED_CLIENT = "https://oauth.reddit.com/grants/installed_client";
    
    
    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    
    
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_RESPONSE_TYPE = "response_type";
    private static final String PARAM_STATE = "state";
    private static final String PARAM_REDIRECT_URI = "redirect_uri";
    private static final String PARAM_DURATION = "duration";
    private static final String PARAM_SCOPE = "scope";
    private static final String PARAM_GRANT_TYPE = "grant_type";
    private static final String PARAM_CODE = "code";
    private static final String PARAM_DEVICE_ID = "device_id";
    
    
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    
    
    private final String userAgent;
    
    
    private OAuthClient oAuthClient;
    
    
    private RedditApp redditApp;
    
    
    public RedditOAuthAgent(String userAgent, RedditApp redditApp) {
        this(userAgent, redditApp, new OAuthClient(new URLConnectionClient()));
    }
    
    
    public RedditOAuthAgent(String userAgent, RedditApp redditApp, OAuthClient oAuthClient) {
        this.userAgent = userAgent;
        this.redditApp = redditApp;
        this.oAuthClient = oAuthClient;
    }
    
    
    public synchronized String generateCodeFlowURI(RedditScopeBuilder scopeBuilder, RedditDuration duration) {
        
        // Set parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_CLIENT_ID, redditApp.getClientID());
        params.put(PARAM_RESPONSE_TYPE, "code");
        params.put(PARAM_STATE, UUID.randomUUID().toString());
        params.put(PARAM_REDIRECT_URI, redditApp.getRedirectURI());
        params.put(PARAM_DURATION, duration.value());
        params.put(PARAM_SCOPE, scopeBuilder.build());
        
        // Create URI
        return REDDIT_AUTHORIZE + KeyValueFormatter.format(params, true);
        
    }
    
    
    public synchronized String generateImplicitFlowURI(RedditScopeBuilder scopeBuilder) {
        
        // Set parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_CLIENT_ID, redditApp.getClientID());
        params.put(PARAM_RESPONSE_TYPE, "token");
        params.put(PARAM_STATE, UUID.randomUUID().toString());
        params.put(PARAM_REDIRECT_URI, redditApp.getRedirectURI());
        params.put(PARAM_SCOPE, scopeBuilder.build());
        
        // Create URI
        return REDDIT_AUTHORIZE + KeyValueFormatter.format(params, true);
        
    }
    
    
    private void addUserAgent(OAuthClientRequest request) {
        request.addHeader(HEADER_USER_AGENT, userAgent);
    }
    
    
    private void addBasicAuthentication(OAuthClientRequest request, RedditApp app) {
        String authString = app.getClientID() + ":" + app.getClientSecret();
       // String authStringEnc = DatatypeConverter.printBase64Binary(authString.getBytes());
        String authStringEnc = new String(Base64.getEncoder().encode(authString.getBytes()));

        request.addHeader(HEADER_AUTHORIZATION, "Basic " + authStringEnc);
    }
    
    
    public synchronized RedditToken token(String code) throws RedditOAuthException {
        
        try {
        
            // Set general values of the request
            OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.REDDIT)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(redditApp.getClientID())
                .setClientSecret(redditApp.getClientSecret())
                .setRedirectURI(redditApp.getRedirectURI())
                .setParameter(PARAM_CODE, code)
                .buildBodyMessage();
            
            // Add the user agent
            addUserAgent(request);
            
            // Add basic authentication
            addBasicAuthentication(request, redditApp);
            
            // Return a wrapper controlled by jReddit
            return new RedditToken(oAuthClient.accessToken(request));
        
        } catch (OAuthSystemException oase) {
            throw new RedditOAuthException(oase);
        } catch (OAuthProblemException oape) {
            throw new RedditOAuthException(oape);
        }

    }
    
    
    public synchronized boolean refreshToken(RedditToken rToken) throws RedditOAuthException {
        
        try {
            
            // Check whether the token can be refreshed
            if (rToken.isRefreshable()) {
            
                // Set general values of the request
                OAuthClientRequest request = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.REDDIT)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(rToken.getRefreshToken())
                    .buildBodyMessage();
                
                // Add the user agent
                addUserAgent(request);
                
                // Add basic authentication
                addBasicAuthentication(request, redditApp);
                
                // Return a wrapper controlled by jReddit
                rToken.refresh(oAuthClient.accessToken(request));
                
                return true;
            
            } else {
                
                // The token cannot be refreshed
                return false;
                
            }
        
        
        } catch (OAuthSystemException oase) {
            throw new RedditOAuthException(oase);
        } catch (OAuthProblemException oape) {
            throw new RedditOAuthException(oape);
        }
        
    }
    
    
    public synchronized RedditToken tokenAppOnly(boolean confidential) throws RedditOAuthException {
        
        try {
        
            // Set general values of the request
            TokenRequestBuilder builder = OAuthClientRequest
                .tokenProvider(OAuthProviderType.REDDIT)
                .setParameter(PARAM_GRANT_TYPE, confidential ? GRANT_TYPE_CLIENT_CREDENTIALS : GRANT_TYPE_INSTALLED_CLIENT)
                .setClientId(redditApp.getClientID())
                .setClientSecret(redditApp.getClientSecret());
            
            // If it is not acting on behalf of a unique client, a unique device identifier must be generated:
            if (!confidential) {
                builder = builder.setParameter(PARAM_DEVICE_ID, UUID.randomUUID().toString());
            }
            
            // Build the request
            OAuthClientRequest request = builder.buildBodyMessage();
            
            // Add the user agent
            addUserAgent(request);
            
            // Add basic authentication
            addBasicAuthentication(request, redditApp);
            
            // Return a wrapper controlled by jReddit
            return new RedditToken(oAuthClient.accessToken(request));
            
        } catch (OAuthSystemException oase) {
            throw new RedditOAuthException(oase);
        } catch (OAuthProblemException oape) {
            throw new RedditOAuthException(oape);
        }
        
    }
    
    
    public synchronized RedditToken tokenFromInfo(String accessToken, String tokenType, long expiresIn, String scope) {
        return new RedditToken(accessToken, tokenType, expiresIn, scope);
    }
    
    
    public boolean revoke(RedditToken token, boolean revokeAccessTokenOnly) {
        // TODO: Implement
        // https://www.reddit.com/api/v1/revoke_token
        // In POST data: token=TOKEN&token_type_hint=TOKEN_TYPE
        // TOKEN_TYPE: refresh_token or access_token
        // 
        return true;
    }
    
}
