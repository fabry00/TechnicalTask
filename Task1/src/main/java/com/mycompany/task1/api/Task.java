package com.mycompany.task1.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    private long id;

    private String content;

    private Task() {
        // Jackson deserialization
    }

    private Task(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
    
    public static class Builder {
        
        private long id;
        private String content;
        
        public Builder withId(long id) {
            this.id = id;
            return this;
        }
        
        public Builder withContent(String content){
            this.content = content;
            return this;
        }
        
        public Task build() {
            return new Task(this.id, this.content);
        }
    }
}
