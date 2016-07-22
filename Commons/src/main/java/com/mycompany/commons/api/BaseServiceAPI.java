package com.mycompany.commons.api;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.commons.headers.Header;
import io.dropwizard.client.HttpClientBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 *
 * @author fabry
 */
public abstract class BaseServiceAPI implements IAPI{
    private final HttpClient httpClient;
    private final URI uri;
    
    public BaseServiceAPI(URI uri){
        MetricRegistry metric = new MetricRegistry();
        httpClient = new HttpClientBuilder(metric).build(getClass().getName());
        this.uri = uri;
    }   
    
    public URI getUri() {
        return uri;
    }
    
    public HttpClient getClient() {
        return httpClient;
    }
    
    public IServiceInfo getServiceInfo() throws SystemUnreachable {        
        try {
            HttpGet request = new HttpGet(this.uri);
            request.setHeader(Header.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            HttpResponse response = httpClient.execute(request);
            ObjectMapper mapper = new ObjectMapper();
            IServiceInfo myObject = mapper.readValue(response.getEntity().getContent(), 
                                                    ServiceInfo.class);
            return myObject;
        } catch (IOException ex) {
            Logger.getLogger(BaseServiceAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        throw new SystemUnreachable("unable to connect");
    }
}
