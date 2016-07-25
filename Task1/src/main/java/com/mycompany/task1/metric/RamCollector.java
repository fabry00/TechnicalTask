package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.BaseCollector;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Collect the metrics
 *
 * @author Fabrizio Faustinoni
 */
public class RamCollector extends BaseCollector {

    private static final String NAME = "RAM";
    private final int seconds;
    private boolean stop = false;
    private Runtime runtime = Runtime.getRuntime();
    private Sigar sigar = new Sigar();

    public RamCollector(int seconds) {

        this.seconds = seconds;
    }

    @Override
    public void run() {
        while (!stop) {

            Metric metric = new Metric();
            metric.setName(NAME);
            Long value = getUsedMemory();
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

    private Long getUsedMemory() {
        try {
            Mem mem = sigar.getMem();

            /*com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            long physicalMemorySize = os.getTotalPhysicalMemorySize();
            long freePhysicalMemory = os.getFreePhysicalMemorySize();*/
            return mem.getActualUsed();
        } catch (SigarException ex) {
            Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, "Eror getUsedMemory", ex);
        }
        return null;
    }

}
