package com.mycompany.aggregator;

import com.mycompany.aggregator.health.HealthCheckTask;
import com.mycompany.commons.api.IAPI;
import com.mycompany.commons.resource.DefaultResource;
import com.mycompany.commons.resource.IDefaultResource;
import com.mycompany.task.api.ProcessServiceAPITask2;
import com.mycompany.task1.api.Task1API;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.net.URISyntaxException;
import java.util.Set;

public class ServiceAggregatorApplication extends Application<ServiceAggregatorConfiguration> {

    private Set<IAPI> apis;
    private Task1API task1Api;
    private ProcessServiceAPITask2 task2Api;

    public static void main(final String[] args) throws Exception {
        new ServiceAggregatorApplication().run(args);
    }

    @Override
    public String getName() {
        return ServiceAggregatorConfiguration.SERVICE_NAME;
    }

    @Override
    public void initialize(final Bootstrap<ServiceAggregatorConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ServiceAggregatorConfiguration configuration,
            final Environment environment) throws URISyntaxException {

        environment.healthChecks().register(ServiceAggregatorConfiguration.SERVICE_NAME,
                getHealthCheck());

        environment.jersey().register(getDefault());

        new Thread(new LinerarRegression()).start();

    }

    private IDefaultResource getDefault() {
        IDefaultResource defaultRes = new DefaultResource.Builder()
                .withName(ServiceAggregatorConfiguration.SERVICE_NAME)
                .withDesc(ServiceAggregatorConfiguration.SERVICE_DESC)
                .build();

        return defaultRes;
    }

    private HealthCheckTask getHealthCheck() {

        HealthCheckTask checker = new HealthCheckTask(apis);
        return checker;
    }

}
