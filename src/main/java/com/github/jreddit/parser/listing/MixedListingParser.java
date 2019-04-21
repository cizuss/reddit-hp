package com.github.jreddit.parser.listing;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.entity.imaginary.MixedListingElement;
import com.github.jreddit.parser.exception.RedditParseException;


public class MixedListingParser extends RedditListingParser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MixedListingParser.class);
    
    
    public List<MixedListingElement> parse(String jsonText) throws RedditParseException {
        
        // Parse to a list of things
        List<Thing> things = this.parseGeneric(jsonText);
        
        // List of comment and submission mixed elements
        List<MixedListingElement> mixedElements = new LinkedList<MixedListingElement>();
        
        // Iterate over things
        for (Thing t : things) {
            
            if (t instanceof Comment) {
                mixedElements.add((Comment) t);
            } else if (t instanceof Submission) {
                mixedElements.add((Submission) t);
            } else {
                LOGGER.warn("Encountered an unexpected reddit thing (" + t.getKind().value() + "), skipping it.");
            }
            
        }
        
        // Return result
        return mixedElements;
        
    }
    
}
