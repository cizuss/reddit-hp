package reddithottestposts.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import reddithottestposts.entities.UserRedditPost;
import reddithottestposts.entities.UserRedditPostId;

import java.util.Date;
import java.util.List;

public interface UserRedditPostRepository  extends CrudRepository<UserRedditPost, UserRedditPostId> {
    @Query("select p from UserRedditPost p where p.email = :email and p.createdAt < :createdAt")
    List<UserRedditPost> findByEmailWithCreatedAtAfter(@Param("email") String email, @Param("createdAt") Date createdAt);
}
