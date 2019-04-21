package com.github.jreddit.request.action;

import com.github.jreddit.request.RedditPostRequest;

public abstract class MarkActionRequest extends RedditPostRequest {

    
    public MarkActionRequest(String fullname) {
        this.addBodyParameter("id", fullname);
    }

}
