package com.mycompany.task1.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IMetric {
    
    @JsonProperty
    public String getName();
    @JsonProperty
    public String getValue();
    
}
