package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.IMetricCollector;
import com.mycompany.task1.metric.interfaces.IMetricListener;
import com.mycompany.task1.resources.MetricResource;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage all the collectors
 *
 * @author Fabrizio Faustinoni
 */
public class MetricManager {

    private List<IMetricCollector> colelctors;
    private List<Thread> coolThreads = new ArrayList<>();

    public void startCollector(int seconds) {
        colelctors
                = Collections.unmodifiableList(
                        new MetricFactory().createCollectors(seconds));

        colelctors.stream().forEach((collector) -> {
            Thread t = new Thread(collector);
            coolThreads.add(t);
            t.start();
            
        });
    }

    public List<IMetricCollector> getCollectors() {

        return colelctors;
    }

    public void addListener(IMetricListener listener) {
        colelctors.stream().forEach((collector) -> {
            collector.addListener(listener);
        });
    }
}
