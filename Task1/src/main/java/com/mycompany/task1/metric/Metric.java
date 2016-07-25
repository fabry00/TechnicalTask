package com.mycompany.task1.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class Metric {

    private String name;
    private Object value;

    public Metric() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @JsonProperty
    public String getName() {
        return this.name;
    }

    @JsonProperty
    public Object getValue() {
        return this.value;
    }

    
}
