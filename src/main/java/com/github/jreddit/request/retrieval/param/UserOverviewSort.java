package com.github.jreddit.request.retrieval.param;


public enum UserOverviewSort {
    
    NEW("new"), 
    HOT("hot"), 
    TOP("top"), 
    COMMENTS("controversial");

    private final String value;

    UserOverviewSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
