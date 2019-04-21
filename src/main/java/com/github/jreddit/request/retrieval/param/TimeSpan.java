package com.github.jreddit.request.retrieval.param;


public enum TimeSpan {

    HOUR("hour"), 
    DAY("day"), 
    WEEK("week"), 
    MONTH("month"), 
    YEAR("year"),
    ALL("all");

    private final String value;

    TimeSpan(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
