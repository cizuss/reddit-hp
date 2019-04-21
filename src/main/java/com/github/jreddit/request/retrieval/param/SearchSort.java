package com.github.jreddit.request.retrieval.param;


public enum SearchSort {

    HOT("hot"), 
    RELEVANCE("relevance"), 
    NEW("new"), 
    TOP("top"), 
    COMMENTS("comments");

    private final String value;

    SearchSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
