package com.mycompany.commons.resource;

import com.mycompany.commons.api.IServiceInfo;
import com.codahale.metrics.annotation.Timed;
import javax.ws.rs.GET;

public interface IDefaultResource {
    
    @GET
    @Timed
    public IServiceInfo serviceInfo();
    
}
