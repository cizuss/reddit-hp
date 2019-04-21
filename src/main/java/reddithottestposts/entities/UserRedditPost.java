package reddithottestposts.entities;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(UserRedditPostId.class)
public class UserRedditPost {
    @Column(name = "email")
    @Id
    private String email;

    @Column(name = "post_url")
    @Id
    private String postUrl;

    @Column(name = "created_at")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}
