package com.mycompany.task1.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.mycompany.commons.api.BaseServiceAPI;
import com.mycompany.commons.api.SystemUnreachable;
import com.mycompany.commons.headers.Header;
import com.mycompany.task1.metric.Metric;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task1API extends BaseServiceAPI {

    private final Logger log = LoggerFactory.getLogger(Task1API.class);
    public static final String TASK = "/task/";
    public static final String TASK_LIST = "metrics";

    public Task1API(URI uri) {
        super(uri);
    }

    public Metric[] getMetrics() throws SystemUnreachable {
        try {
            URIBuilder builder = new URIBuilder(getUri() + TASK + TASK_LIST);

            HttpGet request = new HttpGet(builder.build());
            request.setHeader(Header.CONTENT_TYPE, MediaType.APPLICATION_JSON);

            HttpResponse response = getClient().execute(request);
            ObjectMapper mapper = new ObjectMapper();
            Metric[] myObject = mapper.readValue(response.getEntity().getContent(),
                    Metric[].class);

            return myObject;
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } catch (URISyntaxException ex) {
            log.error(ex.getMessage());
        }

        throw new SystemUnreachable("unable to connect");
    }
}
