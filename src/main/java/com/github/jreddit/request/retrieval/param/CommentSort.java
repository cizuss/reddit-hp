package com.github.jreddit.request.retrieval.param;


public enum CommentSort {

    CONFIDENCE("confidence"),
    NEW("new"), 
    TOP("top"), 
    CONTROVERSIAL("controversial"), 
    OLD("old"), 
    QA("qa");

    private final String value;

    CommentSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
