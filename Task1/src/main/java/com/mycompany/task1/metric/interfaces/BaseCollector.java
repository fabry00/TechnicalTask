package com.mycompany.task1.metric.interfaces;


import com.mycompany.task1.metric.Metric;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Fabrizio Faustinoni
 */
public abstract class BaseCollector implements IMetricCollector {

    Set<IMetricListener> listeners = new HashSet<>();

    @Override
    public void addListener(IMetricListener listener) {

        listeners.add(listener);
    }
    
    protected void fire(Metric metric) {
        listeners.stream().forEach((listener) -> {
            listener.newMetric(metric);
        });
    }
}
