package com.github.jreddit.parser.entity;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToBoolean;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToDouble;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToInteger;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import java.util.List;

import org.json.simple.JSONObject;

import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.entity.imaginary.MixedListingElement;


public class Comment extends Thing implements CommentTreeElement, MixedListingElement {

    private String author;            // Username of the author
    private String parentId;        // Parent identifier
    private String subreddit;        // Subreddit name
    private String subredditId;        // Subreddit identifier
    private String linkId;            // Submission (aka. link) identifier
    private String bodyHTML;        // The body with HTML markup
    private Boolean scoreHidden;    // Whether the score is hidden
    private String body;            // The actual body
    private Boolean edited;         // Edited timestamp
    private double created;         // Created timestamp
    private double createdUTC;      // Created UTC timestamp
    private List<CommentTreeElement> replies;  // Replies if retrieved
    private Integer gilded;            // Amount of times the comment been gilded
    private Integer score;            // Karma score
    private Integer upvotes;        // Number of upvotes that this body received
    private Integer downvotes;      // Number of downvotes that this body received
    
    // Possible fields to add as well:
//    private String bannedBy;
//    String likes;
//    private String approvedBy;
//    private String authorFlairCSSClass;
//    private String authorFlairText;
//    String num_reports = null;
//    String distinguished = null;

    public Comment(JSONObject obj) {
        super(safeJsonToString(obj.get("name")));
        
        this.setAuthor(safeJsonToString(obj.get("author")));
        this.setParentId(safeJsonToString(obj.get("parent_id")));
        this.setBody(safeJsonToString(obj.get("body")));
        this.setEdited(safeJsonToBoolean(obj.get("edited")));
        this.setCreated(safeJsonToDouble(obj.get("created")));
        this.setCreatedUTC(safeJsonToDouble(obj.get("created_utc")));
        this.setGilded(safeJsonToInteger(obj.get("gilded")));
        this.setScore(safeJsonToInteger(obj.get("score")));
        this.setUpvotes(safeJsonToInteger(obj.get("ups")));
        this.setDownvotes(safeJsonToInteger(obj.get("downs")));
        this.setSubreddit(safeJsonToString(obj.get("subreddit")));
        this.setSubredditId(safeJsonToString(obj.get("subreddit_id")));
        this.setLinkId(safeJsonToString(obj.get("link_id")));
        this.setBodyHTML(safeJsonToString(obj.get("body_html")));
        this.setScoreHidden(safeJsonToBoolean(obj.get("score_hidden")));

    }
    
    
    public List<CommentTreeElement> getReplies() {
        return this.replies;
    }
    
    
    public void setReplies(List<CommentTreeElement> replies) {
        this.replies = replies;
    }

    
    public String getAuthor() {
        return author;
    }

    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    public Integer getGilded() {
        return gilded;
    }

    public void setGilded(Integer gilded) {
        this.gilded = gilded;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }
    
    public double getCreatedUTC() {
        return createdUTC;
    }

    public void setCreatedUTC(double createdUTC) {
        this.createdUTC = createdUTC;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    
    public String getSubreddit() {
        return subreddit;
    }

    
    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    
    public String getSubredditId() {
        return subredditId;
    }

    
    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    
    public String getLinkId() {
        return linkId;
    }

    
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    
    public String getBodyHTML() {
        return bodyHTML;
    }

    
    public void setBodyHTML(String bodyHTML) {
        this.bodyHTML = bodyHTML;
    }

    
    public Boolean isScoreHidden() {
        return scoreHidden;
    }

    
    public void setScoreHidden(Boolean scoreHidden) {
        this.scoreHidden = scoreHidden;
    }

    @Override
    public String toString() {
        return "Comment(" + identifier + ")<" + ((body.length() > 10) ? body.substring(0, 10) : body) + ">";
    }    
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Comment && this.getFullName().equals(((Comment) other).getFullName());
    }
    
    @Override
    public int hashCode() {
        return this.hashCode() * this.getFullName().hashCode();
    }

    @Override
    public int compareTo(Thing o) {
        return this.getFullName().compareTo(o.getFullName());
    }
    
}
