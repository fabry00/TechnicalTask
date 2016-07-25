package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.BaseCollector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Collect the metrics
 *
 * @author Fabrizio Faustinoni
 */
public class CpuCollector extends BaseCollector {

    private static final String NAME = "CPU";
    private final int seconds;
    private boolean stop = false;
    private Sigar sigar = new Sigar();

    public CpuCollector(int seconds) {

        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (!stop) {
            Metric metric = new Metric();
            metric.setName(NAME);
            Double value = getCpuUsage();
            if (value != null) {
                metric.setValue(value);
                fire(metric);
            }

            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Double getCpuUsage() {
       // System.out.println(System.getProperty("java.library.path"));
        try {
            
            CpuPerc perc = sigar.getCpuPerc();

            return perc.getCombined()*100;
        } catch (SigarException ex) {
            Logger.getLogger(CpuCollector.class.getName()).log(Level.SEVERE, "Error retreiving cpu usage", ex);
        }
        
        return null;
    }

}
