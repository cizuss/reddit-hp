package com.github.jreddit.request.retrieval.submissions;

import com.github.jreddit.request.retrieval.ListingRequest;
import com.github.jreddit.request.retrieval.param.TimeSpan;
import com.github.jreddit.request.retrieval.param.UserOverviewSort;
import com.github.jreddit.request.retrieval.param.UserSubmissionsCategory;

public class SubmissionsOfUserRequest extends ListingRequest {

    private static final String ENDPOINT_FORMAT = "/user/%s/%s.json?%s";
    
    private UserSubmissionsCategory category;
    private String username;
    
    
    public SubmissionsOfUserRequest(String username, UserSubmissionsCategory category) {
        this.username = username;
        this.category = category;
    }

    public SubmissionsOfUserRequest setSort(UserOverviewSort sort) {
        this.addParameter("sort", sort.value());
        return this;
    }
    
    public SubmissionsOfUserRequest setTime(TimeSpan time) {
        this.addParameter("t", time.value());
        return this;
    }
    
    public SubmissionsOfUserRequest setShowGiven() {
        this.addParameter("show", "given");
        return this;
    }

    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, username, category.value(), this.generateParameters());
    }
    
}
