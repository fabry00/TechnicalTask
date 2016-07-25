package com.mycompany.aggregator.health;
import com.codahale.metrics.health.HealthCheck;
import com.mycompany.commons.api.IAPI;
import com.mycompany.commons.api.IServiceInfo;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HealthCheckTask extends HealthCheck {
    private final Logger log = LoggerFactory.getLogger(HealthCheckTask.class);
    private final Set<IAPI> apis;
    

    public HealthCheckTask(Set<IAPI> apis) {
        this.apis = apis;

    }

    @Override
    protected Result check() throws Exception {
        this.log.info("checking");
        Result result = Result.healthy();
        for(IAPI api : this.apis){
            log.info("Checking API: "+api.getClass().getName());
            IServiceInfo resp = api.getServiceInfo();

            if(resp.getName().isEmpty()) {
                log.warn("API: "+api.getClass().getName()+" NOT AVAILABLE");
                result = Result.unhealthy("ServiceName is empty for: "
                        +api.getClass().getName());
                break;
            }
            if(resp.getDescription().isEmpty()) {
                log.warn("API: "+api.getClass().getName()+" NOT AVAILABLE");
                result = Result.unhealthy("ServiceDescription is empty for: "
                        +api.getClass().getName());
                break;
            }
            log.info("API: "+api.getClass().getName()+" AVAILABLE");
        }
        return result;
    }
}
