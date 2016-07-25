package com.mycompany.task1.commandline;

import com.mycompany.task1.metric.Metric;
import com.mycompany.task1.metric.interfaces.IMetricListener;



/**
 *
 * @author Fabrizio Faustinoni
 */
public class MetricPrinter implements IMetricListener{

    @Override
    public void newMetric(Metric metric) {
        System.out.println(metric.getName()+" "+metric.getValue());
    }
    
}
