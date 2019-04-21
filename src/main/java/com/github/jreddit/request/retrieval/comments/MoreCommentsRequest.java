package com.github.jreddit.request.retrieval.comments;

import java.util.List;

import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.retrieval.param.CommentSort;
import com.github.jreddit.request.util.KeyValueFormatter;

public class MoreCommentsRequest extends RedditGetRequest {

    
    private static final String ENDPOINT_FORMAT = "/api/morechildren.json?%s";
    
    
    public MoreCommentsRequest(String submissionFullname, List<String> commentIdentifiers) {
        // Neglected optional "id" parameter, as it is only relevant for HTML
        this.addParameter("api_type", "json");
        this.addParameter("link_id", submissionFullname);
        this.addParameter("children", KeyValueFormatter.formatCommaSeparatedList(commentIdentifiers));
    }

    public MoreCommentsRequest setSort(CommentSort sort) {
        this.addParameter("sort", sort.value());
        return this;
    }
    
    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, this.generateParameters());
    }
    
}
