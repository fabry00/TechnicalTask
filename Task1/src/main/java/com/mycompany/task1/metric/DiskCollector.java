package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.BaseCollector;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Collect the metrics
 *
 * @author Fabrizio Faustinoni
 */
public class DiskCollector extends BaseCollector {

    private static final String NAME = "/home";
    private Runtime runtime = Runtime.getRuntime();
    private final int seconds;
    private boolean stop = false;
    private Sigar sigar = new Sigar();

    public DiskCollector(int seconds) {

        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (!stop) {
            Metric metric = new Metric();
            metric.setName(NAME);
            Long value = getDiskInfo();
            if (value != null) {
                metric.setValue(value);
                fire(metric);
            }
            metric.setValue(getDiskInfo());
            fire(metric);

            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Long getDiskInfo() {
        try {
            FileSystemUsage filesystemusage = sigar.getFileSystemUsage("/");
            // Return KB
            return filesystemusage.getUsed();
        } catch (SigarException ex) {
            Logger.getLogger(DiskCollector.class.getName()).log(Level.SEVERE, "getDiskInfo error", ex);
        }
        return null;
    }

}
