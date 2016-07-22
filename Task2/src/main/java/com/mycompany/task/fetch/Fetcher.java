package com.mycompany.task.fetch;

import com.mycompany.commons.api.SystemUnreachable;
import com.mycompany.task1.api.IMetric;
import com.mycompany.task1.api.Task1API;
import com.mycompany.task1.metric.RamCollector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class Fetcher implements Runnable {

    private final int seconds;
    private boolean stop = false;
    private Task1API api;

    public Fetcher(int seconds) throws URISyntaxException {

        URI uri = new URI("http://195.176.181.126:9082");
        this.api = new Task1API(uri);
        this.seconds = seconds;
    }

    @Override
    public void run() {

        while (!stop) {
            System.out.println("Fetch");
            try {
                List<IMetric> metrics = this.api.getMetrics();
                for(IMetric metric : metrics) {
                 System.out.println(metric.getName()+" "+metric.getValue());
                }
            } catch (SystemUnreachable ex) {
                Logger.getLogger(Fetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
