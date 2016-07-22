package com.mycompany.task1.metric.interfaces;

/**
 * Interface metric collector
 *
 * @author Fabrizio Faustinoni
 */
public interface IMetricCollector extends Runnable {

    public void addListener(IMetricListener listener);

}
