package reddithottestposts.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRedditPostId implements Serializable {
    private String email;
    private String postUrl;
}
