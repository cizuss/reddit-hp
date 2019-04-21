package reddithottestposts.services;

import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.exception.RedditParseException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reddithottestposts.entities.UserRedditPost;
import reddithottestposts.repos.UserRedditPostRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private RedditService redditService;

    @Autowired
    private UserRedditPostRepository userRedditPostRepository;

    private final Config config = ConfigFactory.load();
    private final String senderEmail = config.getString("sender-email");
    private final String senderPassword = config.getString("sender-password");

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
    private Properties setProps() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        return props;
    }

    public void sendEmailWithSubmission(Submission submission, String recipient) {
        Properties props = setProps();

        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, senderPassword);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient,false));
            msg.setSubject("Check out this awesome reddit post!");

            String text = getEmailBody(submission);

            msg.setContent(text, "text/html");
            msg.setSentDate(new Date());

            Transport.send(msg);

            System.out.println("Message sent.");
        } catch (MessagingException e)
        {
            System.out.println("Error, cause: " + e);
        }
    }
}
