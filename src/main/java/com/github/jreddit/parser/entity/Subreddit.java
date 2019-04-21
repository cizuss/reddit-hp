package com.github.jreddit.parser.entity;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToBoolean;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToDouble;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToLong;
import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import org.json.simple.JSONObject;


public class Subreddit extends Thing {
    
    private String displayName;
    private String title;
    private String url;
    private String description;
    private String subredditType;
    
    private double created;
    private double createdUTC;

    private Boolean nsfw;

    private Long subscribers;
    
    // Other possible fields
    
    // Submit text HTML
//    String submit_text_html = null;
    
    // Whether user is banned
//    Boolean user_is_banned = null;
    
    // Submit text
//    String submit_text = "submit text for subreddit";
    
    // Header image
//    String header_img = "http://a.thumbs.redditmedia.com/yyL5sveWcgkCPKbr.png";
    
    // Description in HTML markup
//    String description_html = "&lt;div&gt;HTML description for subreddit&lt;/d&gt;";
    
    // Whether user is moderator
//    Boolean user_is_moderator = null;
    
    // Header title
//    String header_title = "Header title for subreddit";
    
    // Submit link title
//    String submit_link_label = "Submit link label";
    
    // Accounts active
//    String accounts_active = null;
    
    // Whether it allows public traffic
//    Boolean public_traffic = true;
    
    // Size of header
//    JSONArray header_size = JsonHelpers.jsonArrayOf(160, 64);
    
    // Submit text label
//    String submit_text_label = "Submit text label";
    
    // Whether user is contributor
//    Boolean user_is_contributor = null;
    
    // Public description
//    String public_description = "Public description of subreddit";
    
    // Amount of minutes the comment score is hidden
//    long comment_score_hide_mins = 0;
    
    // What types of submissions are allowed
//    String submission_type = "any";
    
    // Whether the user is contributor
//    Boolean user_is_subscriber = null;
    
    
    public Subreddit(JSONObject obj) {
        super(safeJsonToString(obj.get("name")));
        
        setDisplayName(safeJsonToString(obj.get("display_name")));
        setTitle(safeJsonToString(obj.get("title")));
        setURL(safeJsonToString(obj.get("url")));
        setCreated(safeJsonToDouble(obj.get("created")));
        setCreatedUTC(safeJsonToDouble(obj.get("created_utc")));
        setNSFW(safeJsonToBoolean(obj.get("over18")));
        setSubscribers(safeJsonToLong(obj.get("subscribers")));
        setDescription(safeJsonToString(obj.get("description")));
        setSubredditType(safeJsonToString(obj.get("subreddit_type")));
        
    }
    
    private void setCreated(double created) {
        this.created = created;
    }

    private void setCreatedUTC(double createdUTC) {
        this.createdUTC = createdUTC;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private void setNSFW(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    private void setSubscribers(long subscribers) {
        this.subscribers = subscribers;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setURL(String url) {
        this.url = url;
    }

    
    public double getCreated() {
        return created;
    }

    
    public double getCreatedUTC() {
        return createdUTC;
    }

    
    public String getDescription() {
        return description;
    }

    
    public String getDisplayName() {
        return displayName;
    }

    
    public long getSubscribers() {
        return subscribers;
    }

    
    public String getTitle() {
        return title;
    }

    
    public String getURL() {
        return url;
    }

    
    public Boolean isNSFW() {
        return nsfw;
    }
    
    
    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String type) {
        this.subredditType = type;
    }

    @Override
    public String toString() {
        return "Subreddit(" + this.getFullName() + ")<" + this.getDisplayName() + ">";
    }
    
    @Override
    public boolean equals(Object other) {
        return (other instanceof Subreddit && this.getFullName().equals(((Subreddit) other).getFullName()));
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