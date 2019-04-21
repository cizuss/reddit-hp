package com.github.jreddit.request.retrieval.param;


public enum SubmissionSort {

    HOT("hot"), 
    NEW("new"), 
    RISING("rising"), 
    CONTROVERSIAL("controversial"), 
    TOP("top");

    private final String value;

    SubmissionSort(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
