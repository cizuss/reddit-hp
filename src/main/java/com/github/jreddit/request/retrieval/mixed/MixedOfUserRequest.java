package com.github.jreddit.request.retrieval.mixed;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.TimeSpan;
import com.github.jreddit.request.retrieval.param.UserMixedCategory;
import com.github.jreddit.request.retrieval.param.UserOverviewSort;

public class MixedOfUserRequest extends ListingRequest {

    
    private static final String ENDPOINT_FORMAT = "/user/%s/%s.json?%s";
    
    private UserMixedCategory category;
    private String username;
    
    
    public MixedOfUserRequest(String username, UserMixedCategory category) {
        this.username = username;
        this.category = category;
    }

    
    public MixedOfUserRequest setSort(UserOverviewSort sort) {
        this.addParameter("sort", sort.value());
        return this;
    }
    
    
    public MixedOfUserRequest setTime(TimeSpan time) {
        this.addParameter("t", time.value());
        return this;
    }
    
    public MixedOfUserRequest setShowGiven() {
        this.addParameter("show", "given");
        return this;
    }

    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, username, category.value(), this.generateParameters());
    }
    
}
