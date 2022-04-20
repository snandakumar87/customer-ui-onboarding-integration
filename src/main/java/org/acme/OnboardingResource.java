package org.acme;

import io.smallrye.reactive.messaging.annotations.Channel;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
