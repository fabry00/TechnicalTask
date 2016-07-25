package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.BaseCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Collect the metrics
 *
 * @author Fabrizio Faustinoni
 */
public class NetworkCollector extends BaseCollector {

    private static final String NAME = "NetworkInterface";
    private final int seconds;
    private boolean stop = false;
    private Sigar sigar = new Sigar();

    public NetworkCollector(int seconds) {

        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (!stop) {
            List<Metric> metrics = getNetworkMetrics();
            if (metrics != null) {
                for (Metric metric : metrics) {
                    fire(metric);
                }
            }
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private List<Metric> getNetworkMetrics() {

        try {
            String[] networks = sigar.getNetInterfaceList();

            List<Metric> metrics = new ArrayList<>();
            for (String inter : networks) {
                Long readedBytes = sigar.getNetInterfaceStat(inter).getRxBytes();
                Metric metric = new Metric();
                metric.setName(NAME + "_" + inter + "_rx");
                metric.setValue(readedBytes);
                metrics.add(metric);

                Long sentBytes = sigar.getNetInterfaceStat(inter).getTxBytes();
                Metric metricTx = new Metric();
                metricTx.setName(NAME + "_" + inter + "_tx");
                metricTx.setValue(sentBytes);
                metrics.add(metricTx);
            }
            return metrics;
        } catch (SigarException ex) {
            Logger.getLogger(NetworkCollector.class.getName()).log(Level.SEVERE, "Error read list interfaces", ex);
        }

        return null;
    }
}
