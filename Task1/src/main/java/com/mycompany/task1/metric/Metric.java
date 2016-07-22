package com.mycompany.task1.metric;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class Metric {

    private String name;
    private String value;

    public Metric() {

    }

    public Metric(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty
    public String getName() {
        return this.name;
    }

    @JsonProperty
    public String getValue() {
        return this.value;
    }
}
