package com.github.jreddit.parser.util;

import java.util.ArrayList;
import java.util.List;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;

public class CommentTreeUtils {
    
    private CommentTreeUtils() {
        // Empty to disallow the invocation of the default constructor for this utility class
    }

    public static List<CommentTreeElement> flattenCommentTree(List<CommentTreeElement> commentTree) {
        List<CommentTreeElement> target = new ArrayList<CommentTreeElement>();
        flattenCommentTree(commentTree, target);
        return target;
    }

    private static void flattenCommentTree(List<CommentTreeElement> commentTree, List<CommentTreeElement> target) {
        for (CommentTreeElement c : commentTree) {
            target.add(c);
            if (c instanceof Comment) {
                flattenCommentTree(((Comment)c).getReplies(), target);
            }
        }
    }
    
    
    public static String printCommentTree(List<CommentTreeElement> cs) {
        StringBuilder builder = new StringBuilder();
        for (CommentTreeElement c : cs) {
            builder.append(printCommentTree(c, 0));
        }
        return builder.toString();
    }
    
    
    private static String printCommentTree(CommentTreeElement c, int level) {
        
        // Initialize empty buffer
        StringBuilder builder = new StringBuilder();
        
        // Add tabulation
        for (int i = 0; i < level; i++) {
            builder.append("\t");
        }
        
        // Comment string
        builder.append(c.toString());
        builder.append("\n");
        
        // Iterate over children
        if (c instanceof Comment) {
            for (CommentTreeElement child : ((Comment) c).getReplies()) {
                builder.append(printCommentTree(child, level + 1));
            }
        }
        
        // Return the buffer
        return builder.toString();
        
    }
    
}
