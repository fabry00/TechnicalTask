package com.mycompany.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IServiceInfo {

    @JsonProperty
    public String getName();

    public void setName(String name);

    @JsonProperty
    public String getDescription();

    public void setDescription(String description);

}
