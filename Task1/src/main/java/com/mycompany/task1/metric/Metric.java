package com.mycompany.task1.metric;

import com.mycompany.task1.api.IMetric;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class Metric implements IMetric {

    private final String name;
    private final String value;

    public Metric(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
