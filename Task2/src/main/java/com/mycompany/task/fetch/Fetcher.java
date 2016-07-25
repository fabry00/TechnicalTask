package com.mycompany.task.fetch;

import com.mycompany.commons.api.SystemUnreachable;
import com.mycompany.task.saver.Saver;
import com.mycompany.task1.api.Task1API;
import com.mycompany.task1.metric.Metric;
import com.mycompany.task1.metric.RamCollector;
import java.net.URI;
import java.net.URISyntaxException;
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
    private Saver saver;

    public Fetcher(int seconds, Saver saver) throws URISyntaxException {

        URI uri = new URI("http://localhost:8082");
        this.api = new Task1API(uri);
        this.seconds = seconds;
        this.saver = saver;
    }

    @Override
    public void run() {

        while (!stop) {
            try {
                Metric[] metrics = this.api.getMetrics();
                if (metrics == null) {
                    Logger.getLogger(RamCollector.class.getName()).log(Level.SEVERE, "Null metric received");
                    return;
                }
                
                this.saver.save(metrics);
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
