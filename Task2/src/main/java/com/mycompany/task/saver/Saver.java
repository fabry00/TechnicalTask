package com.mycompany.task.saver;

import com.mycompany.task1.metric.Metric;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Saver {

    private List<String> files = new ArrayList<>();

    public Saver() {

    }

    public void save(Metric[] metrics) {
        for (Metric metric : metrics) {
            File file = getFile(metric);
            addMetric(metric, file);
        }
    }

    private File getFile(Metric metric) {
        String filename = metric.getName() + ".arff";
        File file = new File(filename);
        if (!file.exists()) {
            try (FileWriter fw = new FileWriter(filename, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println("% 1. Title: "+metric.getName());
                out.println("%");
                out.println("@RELATION "+metric.getName());
                out.println("@ATTRIBUTE value NUMERIC");
                out.println("");
                out.println("@DATA");
            } catch (IOException e) {
                Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, "Unable to create file", e);
            }
        }

        return file;
    }

    private void addMetric(Metric metric, File file) {
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println(metric.getValue());
                
            } catch (IOException e) {
                Logger.getLogger(Saver.class.getName()).log(Level.SEVERE, "Unable to add metric", e);
            }
    }

}
