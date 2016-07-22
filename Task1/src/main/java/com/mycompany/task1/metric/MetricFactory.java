package com.mycompany.task1.metric;

import com.mycompany.task1.metric.interfaces.IMetricCollector;
import java.util.ArrayList;
import java.util.List;

/**
 * MetricFactory
 * @author Fabrizio Faustinoni
 */
public class MetricFactory {
    
    
    public List<IMetricCollector> createCollectors(int seconds) {
        
        List<IMetricCollector> colllectors = new ArrayList<>();
        colllectors.add(new RamCollector(seconds));
        colllectors.add(new CpuCollector(seconds));
        colllectors.add(new NetworkCollector(seconds));
        colllectors.add(new DiskCollector(seconds));
        return colllectors;
    }
}
