package reddithottestposts.services;

import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.exception.RedditParseException;
import com.sendgrid.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reddithottestposts.entities.UserRedditPost;
import reddithottestposts.repos.UserRedditPostRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private RedditService redditService;

    @Autowired
    private UserRedditPostRepository userRedditPostRepository;

    private final Config config = ConfigFactory.load();
    private final String sendgridApyKey = config.getString("sendgrid-api-key");

    private final SendGrid sg = new SendGrid(sendgridApyKey);

    public void sendEmailWithRandomPost(String recipient) throws RedditOAuthException, RedditParseException {
        List<String> subreddits = Arrays.asList("hearthstone", "summonerschool", "jaxmains", "tryndameremains", "rivenmains", "scala");
        Submission post = redditService.getRandomPostFromSubreddits(subreddits, recipient);

        sendEmailWithSubmission(post, recipient);
        UserRedditPost newPost = new UserRedditPost();
        newPost.setEmail(recipient);
        newPost.setCreatedAt(new Date());
        newPost.setPostUrl(post.getURL());
        userRedditPostRepository.save(newPost);
    }

    private String getEmailBody(Submission submission) {
        String permalink = submission.getPermalink();
        String message = String.format("https://www.reddit.com/%s", permalink.substring(1, permalink.length() - 1));
        StringBuilder sb = new StringBuilder();

        sb.append("<p>");
        sb.append(message);
        sb.append("</p>");

        if (submission.getURL().endsWith(".jpg") || submission.getURL().endsWith(".jpeg")) {
            sb.append(String.format("<a href=%s>\n\t<img src=\"%s\" />\n</a>", submission.getURL(), submission.getURL()));
        }

        return sb.toString();

    }

    private void sendEmail(String subject, String body, String recipient) {
        System.out.println("Sending email with SENDGRID");

        Email from = new Email("no-reply@awesome.com");
        Email to = new Email(recipient);
        Content content = new Content("text/html", body);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendEmailWithSubmission(Submission submission, String recipient) {
        String body = getEmailBody(submission);
        String subject = "Here is a cool reddit post for you!";

        sendEmail(subject, body, recipient);
    }
}
