package com.github.jreddit.request.retrieval;

import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.request.RedditGetRequest;

public abstract class ListingRequest extends RedditGetRequest {

    
    public ListingRequest setCount(int count) {
        this.addParameter("count", String.valueOf(count));
        return this;
    }
    
    
    public ListingRequest setLimit(int limit) {
        this.addParameter("limit", String.valueOf(limit));
        return this;
    }
    
    
    public ListingRequest setAfter(Thing after) {
        return setAfter(after.getFullName());
    }
    
    
    public ListingRequest setAfter(String after) {
        this.addParameter("after", after);
        return this;
    }
    
    
    public ListingRequest setBefore(Thing before) {
        return setBefore(before.getFullName());
    }
    
    
    public ListingRequest setBefore(String before) {
        this.addParameter("before", before);
        return this;
    }
    
}
