package reddithottestposts;

import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.exception.RedditParseException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reddithottestposts.services.EmailService;
import reddithottestposts.services.RedditService;

@SpringBootApplication
@RestController
public class Application {
    @Autowired
    private RedditService redditService;

    @Autowired
    private EmailService emailService;

    private final Config config = ConfigFactory.load();
    private final String receiverEmail = config.getString("receiver-email");

    @RequestMapping(method = RequestMethod.GET, path = "/send_mail")
    public void sendEmail() throws RedditOAuthException, RedditParseException {
        emailService.sendEmailWithRandomPost(receiverEmail);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
