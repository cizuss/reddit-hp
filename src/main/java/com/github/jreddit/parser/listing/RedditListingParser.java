package com.github.jreddit.parser.listing;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Kind;
import com.github.jreddit.parser.entity.More;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.Subreddit;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.exception.RedditParseException;
import com.github.jreddit.parser.util.JsonUtils;

public class RedditListingParser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RedditListingParser.class);
    
    protected static final JSONParser JSON_PARSER = new JSONParser();
    
    
    public void validate(Object response) throws RedditParseException {
        
        // Check for null
        if (response == null) {
            throw new RedditParseException();
        }
        
        // Check it is a JSON response
        if (!(response instanceof JSONObject)) {
            throw new RedditParseException("not a JSON response");
        }
        
        // Cast to JSON object
        JSONObject jsonResponse = ((JSONObject) response);
        
        // Check for error
        if (jsonResponse.get("error") != null) {
            throw new RedditParseException(JsonUtils.safeJsonToInteger(jsonResponse.get("error")));
        }
        
        // Check that data exists
        if (jsonResponse.get("data") == null && jsonResponse.get("json") == null) {
            throw new RedditParseException("data is missing from listing");
        }
        
    }
    
    
    public List<Thing> parseGeneric(String jsonText) throws RedditParseException {
        return parseGeneric(jsonText, "children");
    }
    
    
    public List<Thing> parseGeneric(String jsonText, String listingName) throws RedditParseException {
        
        try {
        
            // List of submissions
            List<Thing> things = new LinkedList<Thing>();
            
            // Send request to reddit server via REST client
            Object response = JSON_PARSER.parse(jsonText);
            
            // Check for reddit error, can throw a RedditError
            validate(response);
            
            // Cast to a JSON object
            JSONObject object = (JSONObject) response;
            
            // Get the array of children
            JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get(listingName);
    
            // Iterate over array of children
            for (Object element : array) {
                
                // Get the element
                JSONObject data = (JSONObject) element;
                
                // Make sure it is of the correct kind
                String kindData = safeJsonToString(data.get("kind"));
                Object objData = data.get("data");
                
                // If no kind is given
                if (kindData == null) {
                    LOGGER.warn("Kind data missing, skipping it.");
                    
                // If no data is given
                } else if (objData == null || !(objData instanceof JSONObject)) {
                    LOGGER.warn("Object data missing, skipping it.");
                    
                } else {
                    
                    // Attempt to match
                    Kind kind = Kind.match(kindData);
                    
                    // Parse the thing
                    Thing thing = parseThing(kind, ((JSONObject) data.get("data")));
                    
                    // Show warning if failed
                    if (thing == null) {
                        LOGGER.warn("Encountered invalid kind for a listing (" + kindData + "), skipping it.");
    
                    } else {
                        things.add(thing);
                    }
                    
                }
                
            }
            
            // Finally return list of submissions 
            return things;
        
        } catch (ParseException pe) {
            throw new RedditParseException(pe);
        }
        
    }
    
    
    private Thing parseThing(Kind kind, JSONObject data) {
        
        // For a comment
        if (kind == Kind.COMMENT) { 
            return new Comment(data);
            
        // For a submission
        } else if (kind == Kind.LINK) {
            return new Submission(data);
        
        // For a subreddit
        } else if (kind == Kind.SUBREDDIT) { 
            return new Subreddit(data);
          
        // For a more
        } else if (kind == Kind.MORE) { 
            return new More(data);
            
        // In all other cases (null, or of a different type)
        } else {
            return null;
        }
        
    }



}
