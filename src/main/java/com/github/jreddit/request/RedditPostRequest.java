package com.github.jreddit.request;

import java.util.HashMap;
import java.util.Map;

import com.github.jreddit.request.util.KeyValueFormatter;

public abstract class RedditPostRequest {

    
    private Map<String, String> queryParameters;
    
    
    private Map<String, String> bodyParameters;    
    
    
    public RedditPostRequest() {
        queryParameters = new HashMap<String, String>();
        bodyParameters = new HashMap<String, String>();        
    }
    
    
    protected void addQueryParameter(String key, String value) {
        queryParameters.put(key, value);
    }
    
    
    protected void addBodyParameter(String key, String value) {
        bodyParameters.put(key, value);
    }

    protected String generateQueryParameters() {
        return KeyValueFormatter.format(queryParameters, true);
    }

    protected String generateBodyParameters() {
        return KeyValueFormatter.format(bodyParameters, true);
    }

    public abstract String generateRedditURI();

    public String generateBody() {
        return generateBodyParameters();
    }
    
}
