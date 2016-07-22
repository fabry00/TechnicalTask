package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.BaseCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Collect the metrics
 *
 * @author Fabrizio Faustinoni
 */
public class NetworkCollector extends BaseCollector {

    private static final String NAME = "NetworkInterface";
    private final int seconds;
    private boolean stop = false;

    public NetworkCollector(int seconds) {

        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (!stop) {
            List<Metric> metrics = getNetworkMetrics();
            for (Metric metric : metrics) {
                System.out.println(metric.getName() + " " + metric.getValue());
                fire(metric);
            }
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private List<Metric> getNetworkMetrics() {

        int interfaces = 2;
        List<Metric> metrics = new ArrayList<>();
        for (int i = 0; i < interfaces; i++) {
            metrics.add(new Metric(NAME + "_" + i, "123 " + i));

        }
        return metrics;
    }

}
