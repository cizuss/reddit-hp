package com.github.jreddit.parser.entity;


public abstract class Thing implements Comparable<Thing> {
    
    
    protected final Kind kind;

    
    protected final String identifier;
    
    
    protected final String fullName;

    
    public Thing(String name) {
        assert name.contains("_") : "A full name must contain an underscore.";
        this.fullName = name;
        String[] split = name.split("_");
        this.kind = Kind.match(split[0]);
        this.identifier = split[1];
    }
    
    
    public Kind getKind() {
        return kind;
    }
    
    
    public String getIdentifier() {
        return identifier;
    }

    
    public String getFullName() {
        return fullName;
    }
    
}