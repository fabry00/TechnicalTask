package com.mycompany.task1;

import com.mycompany.commons.api.IAPI;
import com.mycompany.commons.health.HealthCheckTask;
import com.mycompany.commons.resource.DefaultResource;
import com.mycompany.commons.resource.IDefaultResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.net.URI;
import java.net.URISyntaxException;
import com.mycompany.task1.api.Task1API;
import com.mycompany.task1.commandline.CommandLine;
import com.mycompany.task1.metric.MetricManager;
import com.mycompany.task1.resources.MetricResource;
import java.util.Arrays;

public class ProcessServiceApplication extends Application<ProcessServiceConfiguration> {

    private final int collectEverySeconds;
    private MetricManager metricManager;

    public static void main(final String[] args) throws Exception {

        int seconds = new CommandLine(args).getInputSeconds();

        String[] argsForService
                = Arrays.copyOfRange(args, 0, 2);
        new ProcessServiceApplication(seconds).run(argsForService);
    }

    public ProcessServiceApplication(int seconds) {
        this.collectEverySeconds = seconds;
    }

    @Override
    public String getName() {
        return ProcessServiceConfiguration.SERVICE_DESC;
    }

    @Override
    public void initialize(final Bootstrap<ProcessServiceConfiguration> bootstrap) {
        metricManager = new MetricManager();
        metricManager.startCollector(collectEverySeconds);
    }

    @Override
    public void run(final ProcessServiceConfiguration configuration,
            final Environment environment) throws URISyntaxException {

        environment.healthChecks().register(ProcessServiceConfiguration.SERVICE_NAME,
                getHealthCheck(configuration, environment));
        environment.jersey().register(getDefault());
        environment.jersey().register(getTaskResource());

        
        
    }

    private IDefaultResource getDefault() {
        IDefaultResource defaultRes = new DefaultResource.Builder()
                .withName(ProcessServiceConfiguration.SERVICE_NAME)
                .withDesc(ProcessServiceConfiguration.SERVICE_DESC)
                .build();

        return defaultRes;
    }

    private MetricResource getTaskResource() {
        MetricResource taskResource
                = new MetricResource(ProcessServiceConfiguration.MAX_TASK_LENGTH);

        metricManager.addListener(taskResource);
        return taskResource;
    }

    private HealthCheckTask getHealthCheck(final ProcessServiceConfiguration configuration,
            final Environment environment) throws URISyntaxException {

        // FIXME --> get from config or discover it
        URI uri = new URI("http://localhost:9082");
        IAPI api = new Task1API(uri);
        HealthCheckTask checker = new HealthCheckTask(api);
        return checker;
    }
}
