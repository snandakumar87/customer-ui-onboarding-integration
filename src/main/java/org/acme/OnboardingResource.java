package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/onboarding")
@ApplicationScoped
public class OnboardingResource {

    @Inject
    KafkaController kafkaController;

    @POST
    @Path("/request/{reqId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void postCase(String json,@javax.ws.rs.PathParam("reqId") String reqId) {

        try {


            kafkaController.produce(reqId,json);


        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}
