package com.mycompany.aggregator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class LinerarRegression implements Runnable {

    private static final String BASE_PATH = "F:/Projects/TechnicalTask/Task2";

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(5 * 1000);
                File dir = new File(BASE_PATH);

                File[] arffs = dir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        return filename.endsWith(".arff");
                    }
                });

                for (File file : arffs) {
                  //  new Thread(new Calculator(file)).start();
                  new Calculator(file).run();
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(LinerarRegression.class.getName()).log(Level.SEVERE, "Thread error", ex);
            }
            break;
        }
    }

    private static class Calculator implements Runnable {

        private File file;

        private Calculator(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                Instances data = new Instances(reader);
                data.setClassIndex(data.numAttributes() - 1);
                LinearRegression rl = new LinearRegression();
                rl.buildClassifier(data); //What after this? or before
                System.out.println(file.getName() + " --> " + data.toSummaryString());

                System.out.println(file.getName() + "w --> " + rl.classifyInstance(data.firstInstance()));

                int n = data.numInstances(), m = data.numAttributes();

                double rmsle = 0;
                double rmsleZero = 0;
                String outputPath = BASE_PATH+"/"+file.getName()+"_gression_output.txt";

                try {
                    // Create file
                    FileWriter fstream = new FileWriter(outputPath);
                    BufferedWriter out = new BufferedWriter(fstream);

                    for (int i = 0; i < n; ++i) {
                        Instance t = data.instance(i);
                        double pred = rl.classifyInstance(t), act = t
                                .value(m - 1);
                        out.write(i + "," + act + "," + pred);
                        out.newLine();

                        if (pred < 1) {
                            if (pred != 0) {
                                System.out.println("member " + i + ":" + pred);
                            }
                            pred = 0;
                        }

                        if (pred > 15) {
                            if (pred != 15) {
                                System.out.println("member " + i + ":" + pred);
                            }
                            pred = 15;
                        }

                        rmsle += (Math.log(pred + 1) - Math.log(act + 1))
                                * (Math.log(pred + 1) - Math.log(act + 1));
                        rmsleZero += Math.log(act + 1) * Math.log(act + 1);

                    }
                    out.close();
                } catch (Exception e) {// Catch exception if any
                    System.err.println("Error: " + e.getMessage());
                }

                rmsle = Math.sqrt(rmsle / n);
                rmsleZero = Math.sqrt(rmsleZero / n);

                System.out.println("# of training data: " + data.numInstances());
                System.out.println("# of testing data: " + data.numInstances());
                System.out.println("RMSLE on testing data: " + rmsle);
                System.out.println("RMSLE on testing data Zero: " + rmsleZero);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(LinerarRegression.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LinerarRegression.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LinerarRegression.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(LinerarRegression.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

}
