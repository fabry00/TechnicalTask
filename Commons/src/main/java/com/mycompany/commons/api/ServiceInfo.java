package com.mycompany.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class ServiceInfo implements IServiceInfo{

    private String name;
    private String description;

    public ServiceInfo() {
    }

    @JsonProperty
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name+": "+description;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!ServiceInfo.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ServiceInfo other = (ServiceInfo) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        
        return this.description.equals(other.description);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        return hash;
    }
    
    
    public static class Builder {

        private String name;
        private String description;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDesc(String desc) {
            this.description = desc;
            return this;
        }

        public IServiceInfo build() {
            IServiceInfo info = new ServiceInfo();
            info.setName(name);
            info.setDescription(description);
            return info;
        }
    }
}
