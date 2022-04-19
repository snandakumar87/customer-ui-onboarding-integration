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

    @Inject
    KafkaDocsController kafkaDocsController;

    @Inject
    @Channel("txn-kafka")
    Publisher<String> transactionPublisher;


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

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    public Publisher<String> stream()
    {
        System.out.println("here");

        return transactionPublisher;
    }


    @POST
    @Path("/documents")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void postCaseDocs(MultipartFormDataInput dataInput, @javax.ws.rs.QueryParam("requestId") String requestId) throws Exception {
        Map<String, List<InputPart>> uploadForm = dataInput.getFormDataMap();

        List<String> docIds = new ArrayList<>();



        List<InputPart> docs = uploadForm.get("uploadedFile");

        System.out.println(docs.size());

        for (InputPart inputPart : docs) {
            int i = 0;

            InputStream inputStream = inputPart.getBody(InputStream.class, null);

            String fileName = getFileName(inputPart.getHeaders());



            byte[] bytes = IOUtils.toByteArray(inputStream);
            String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(bytes, true));

            String responseString = base64;
            System.out.println(responseString);
            kafkaDocsController.produce(requestId,responseString);

        }



    }
    private String getFileName(MultivaluedMap<String, String> header) {


        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {

            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }


}
