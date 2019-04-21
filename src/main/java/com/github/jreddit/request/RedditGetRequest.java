package com.github.jreddit.request;

import java.util.HashMap;
import java.util.Map;

import com.github.jreddit.request.util.KeyValueFormatter;

public abstract class RedditGetRequest {

    
    private Map<String, String> parameters;
    
    
    public RedditGetRequest() {
        parameters = new HashMap<String, String>();
    } 
    
    
    protected void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    protected String generateParameters() {
        return KeyValueFormatter.format(parameters, true);
    }

    public abstract String generateRedditURI();
    
}
