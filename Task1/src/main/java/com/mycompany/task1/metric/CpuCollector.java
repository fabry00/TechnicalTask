package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.BaseCollector;
import com.mycompany.task1.api.IMetric;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Collect the metrics
 *
 * @author Fabrizio Faustinoni
 */
public class CpuCollector extends BaseCollector {

    private static final String NAME = "CPU";
    private final int seconds;
    private boolean stop = false;

    public CpuCollector(int seconds) {

        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (!stop) {
            IMetric metric = new Metric(NAME, "17");
            System.out.println(metric.getName() + " " + metric.getValue());
            fire(metric);

            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
