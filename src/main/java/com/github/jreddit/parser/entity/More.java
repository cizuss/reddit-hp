package com.github.jreddit.parser.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.util.JsonUtils;


public class More extends Thing implements CommentTreeElement {

    
    private List<String> children;
    
    
    private Long count;
    
    
    private String parentId;
    
    
    public More(JSONObject obj) {
        super(Kind.MORE.value() + "_NONE");
        
        // The obj.get("name") and obj.get("id") are neglected, as these
        // are already implicitly included in the children array.
        
        // Retrieve count from JSON
        this.count = JsonUtils.safeJsonToLong(obj.get("count"));
        
        // Retrieve parent identifier from JSON
        this.parentId = JsonUtils.safeJsonToString(obj.get("parent_id"));
        
        // Iterate over children
        this.children = new ArrayList<String>();
        JSONArray jsonChildren = (JSONArray) obj.get("children");
        for (Object child : jsonChildren) {
            this.children.add(JsonUtils.safeJsonToString(child));
        }
        
    }

    
    public List<String> getChildren() {
        return children;
    }

    
    public Long getCount() {
        return count;
    }
    
    
    public int getChildrenSize() {
        return children.size();
    }

    
    public String getParentId() {
        return parentId;
    }

    @Override
    public int compareTo(Thing o) {
        if (!(o instanceof More)) {
            return 1;
        } else {
            return ((More) o).getChildren().equals(this.getChildren()) ? 0 : -1;
        }
        
    }
    
    @Override
    public String toString() {
        return "More()<" + this.getChildrenSize() + " more directly underneath>";
    }
    
}
