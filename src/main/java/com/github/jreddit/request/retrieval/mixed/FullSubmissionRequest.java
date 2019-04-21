package com.github.jreddit.request.retrieval.mixed;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.request.RedditGetRequest;
import com.github.jreddit.request.retrieval.param.CommentSort;

public class FullSubmissionRequest extends RedditGetRequest {

    private static final String ENDPOINT_FORMAT = "/comments/%s.json?%s";

    private String submissionIdentifier;
    
    
    public FullSubmissionRequest(String submissionIdentifier) {
        this.submissionIdentifier = submissionIdentifier;
    }
    
    
    public FullSubmissionRequest(Submission submission) {
        this(submission.getIdentifier());
    }

    public FullSubmissionRequest setSort(CommentSort sort) {
        this.addParameter("sort", sort.value());
        return this;
    }
    
    public FullSubmissionRequest setLimit(int limit) {
        this.addParameter("limit", String.valueOf(limit));
        return this;
    }
    
    
    public FullSubmissionRequest setComment(String commentIdentifier) {
        this.addParameter("comment", commentIdentifier);
        return this;
    }
    
    
    public FullSubmissionRequest setContext(int context) {
        this.addParameter("context", String.valueOf(context));
        return this;
    }
    
    
    public FullSubmissionRequest setDepth(int depth) {
        this.addParameter("depth", String.valueOf(depth));
        return this;
    }
    
    
    public FullSubmissionRequest setShowEdits(boolean showEdits) {
        this.addParameter("showedits", String.valueOf(showEdits));
        return this;
    }
    
    
    public FullSubmissionRequest setShowMore(boolean showMore) {
        this.addParameter("showmore", String.valueOf(showMore));
        return this;
    }
    
    @Override
    public String generateRedditURI() {
        return String.format(ENDPOINT_FORMAT, submissionIdentifier, this.generateParameters());
    }
    
}
