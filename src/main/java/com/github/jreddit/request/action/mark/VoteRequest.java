package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.MarkActionRequest;


public class VoteRequest extends MarkActionRequest {
    
    
    private static final String ENDPOINT_FORMAT = "/api/vote.json?";

    
    public VoteRequest(String fullname, int direction) {
        super(fullname);
        this.addBodyParameter("dir", String.valueOf(direction));
    }
    
    @Override
    public String generateRedditURI() {
        return ENDPOINT_FORMAT;
    }
    
}
