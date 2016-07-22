package com.mycompany.task1.resources;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.task1.api.Task1API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.mycompany.task1.metric.Metric;
import com.mycompany.task1.metric.interfaces.IMetricListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by bartoszjedrzejewski on 03/01/2016.
 */
@Path(Task1API.TASK)
@Produces(MediaType.APPLICATION_JSON)
public class MetricResource implements IMetricListener {

    private final int maxLength;
    private final AtomicLong counter;
    private Map<String, Metric> lastMetrics = new HashMap<>();

    //SLF4J is provided with dropwizard
    Logger log = LoggerFactory.getLogger(MetricResource.class);

    public MetricResource(int maxLength) {
        this.maxLength = maxLength;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    // /taks-list
    // /taks-list?contains=string
    @Path("/" + Task1API.TASK_LIST)
    public Metric[] listTasks() {
        log.info("Fetch received");
        Metric[] metrics = new Metric[lastMetrics.values().size()];
        int counter = 0;
        for (Entry<String, Metric> entr : lastMetrics.entrySet()) {
            System.out.println("Fetched received: "+entr.getValue().getName());
            metrics[counter] = entr.getValue();
        }
        return metrics;
    }

    @Override
    public void newMetric(Metric metric) {
        lastMetrics.put(metric.getName(), metric);
    }
}
