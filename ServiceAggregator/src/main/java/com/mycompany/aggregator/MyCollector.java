package com.mycompany.aggregator;

import com.mycompany.commons.api.SystemUnreachable;
import com.mycompany.task.api.ProcessServiceAPITask2;
import com.mycompany.task1.api.Task1API;
import com.mycompany.task1.metric.Metric;
import com.mycompany.task1.metric.RamCollector;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class MyCollector implements Runnable {

    private int seconds = 60;
    private boolean stop = false;

    private List<String> values = new ArrayList<>();

    public MyCollector() {
        try (FileWriter fw = new FileWriter("RAM", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println("@DATA");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }

    @Override
    public void run() {
        try {
            URI uri = new URI("http://localhost:9082");
            Task1API task1Api = new Task1API(uri);
            ProcessServiceAPITask2 task2Api;
            while (!stop) {
                Metric[] metrics = task1Api.getMetrics();
                if (metrics == null) {
                    return;
                }
                for (Metric metric : metrics) {
                    if (metric == null) {
                        continue;
                    }
                    System.out.println("received metric " + metric.getName() + " " + metric.getValue());
                    values.add(metric.getValue());
                    try (FileWriter fw = new FileWriter(metric.getName(), true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter out = new PrintWriter(bw)) {
                        out.print(metric.getValue() + ",");
                    } catch (IOException e) {
                        //exception handling left as an exercise for the reader
                    }
                }

                try {
                    Thread.sleep(seconds * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(MyCollector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemUnreachable ex) {
            Logger.getLogger(MyCollector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*private void calculatingLinearRegression() {
        Instances reducedInst = new Instances();
        Remove attributeFilter = new Remove();

        attributeFilter.setInvertSelection(true);
        attributeFilter.setAttributeIndicesArray(indices);
        attributeFilter.setInputFormat(reducedInst);

        reducedInst = Filter.useFilter(reducedInst, attributeFilter);
    }*/
}
