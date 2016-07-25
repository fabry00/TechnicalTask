package com.mycompany.task;

import com.mycompany.commons.api.IAPI;
import com.mycompany.commons.health.HealthCheckTask;
import com.mycompany.commons.resource.DefaultResource;
import com.mycompany.commons.resource.IDefaultResource;
import com.mycompany.task.api.ProcessServiceAPITask2;
import com.mycompany.task.commandline.CommandLine;
import com.mycompany.task.fetch.Fetcher;
import com.mycompany.task.resources.TaskResource;
import com.mycompany.task.saver.Saver;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ProcessServiceApplication2 extends Application<ProcessServiceConfiguration2> {

    private final int collectEverySeconds;

    public static void main(final String[] args) throws Exception {
        int seconds = new CommandLine(args).getInputSeconds();

        String[] argsForService
                = Arrays.copyOfRange(args, 0, 2);
        new ProcessServiceApplication2(seconds).run(argsForService);
    }

    public ProcessServiceApplication2(int seconds) {
        this.collectEverySeconds = seconds;
    }

    @Override
    public String getName() {
        return ProcessServiceConfiguration2.SERVICE_DESC;
    }

    @Override
    public void initialize(final Bootstrap<ProcessServiceConfiguration2> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ProcessServiceConfiguration2 configuration,
            final Environment environment) throws URISyntaxException {

        environment.healthChecks().register(ProcessServiceConfiguration2.SERVICE_NAME,
                getHealthCheck(configuration, environment));
        environment.jersey().register(getDefault());
        environment.jersey().register(getTaskResource());

        Saver saver = new Saver();
        Thread t = new Thread(new Fetcher(collectEverySeconds, saver));
        t.start();
    }

    private IDefaultResource getDefault() {
        IDefaultResource defaultRes = new DefaultResource.Builder()
                .withName(ProcessServiceConfiguration2.SERVICE_NAME)
                .withDesc(ProcessServiceConfiguration2.SERVICE_DESC)
                .build();

        return defaultRes;
    }

    private TaskResource getTaskResource() {
        TaskResource resource
                = new TaskResource(ProcessServiceConfiguration2.MAX_TASK_LENGTH);

        return resource;
    }

    private HealthCheckTask getHealthCheck(final ProcessServiceConfiguration2 configuration,
            final Environment environment) throws URISyntaxException {

        /*URI uri = configuration.getServerFactory()
                .build(environment)
                .getURI();
         */
        // FIXME --> get from config or discover it
        URI uri = new URI("http://localhost:9084");
        IAPI api = new ProcessServiceAPITask2(uri);
        HealthCheckTask checker = new HealthCheckTask(api);
        return checker;
    }
}
