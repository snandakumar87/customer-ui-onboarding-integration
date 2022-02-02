package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/onboarding")
@ApplicationScoped
public class OnboardingResource {
    @Inject
    @RestClient
    ProcessService processService;

    @GET
    @Path("/entity/{entityId}/country/{country}/subProductType/{subProductType}/annualSpend/{annualSpend}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuestionnaireRules(@javax.ws.rs.PathParam("entityId") String entityId, @PathParam("country") String country,
                                        @PathParam("subProductType") String subProductType, @PathParam("annualSpend") String annualSpend) {

        try {

            String jsonReq = "{\"Country\":\"" + country + "\",\"Entity Type\":\"" + entityId + "\",\"Sub Product Type\":\""+subProductType+"\",\"Annual Spend\":"+annualSpend+"}";
            System.out.println(jsonReq);
            String response = processService.getQuestionnaireRules(jsonReq);


            HashMap mapResponse = new ObjectMapper().readValue(response, HashMap.class);
            List results = (List) mapResponse.get("decisionResults");

            Map resultListVal = (Map) results.get(0);
            Map result = null;
            System.out.println(resultListVal.get("result").getClass());
            if(resultListVal.get("result").getClass().equals(LinkedHashMap.class)) {

                 result = (Map) resultListVal.get("result");
            } else {
                System.out.println(resultListVal);
                List fields = (List)resultListVal.get("result");
                result = (Map)fields.get(0);
            }


            System.out.println(result);

            QuestionnaireObject questionnaireObject = new QuestionnaireObject();
            questionnaireObject.setGeneralPartnerName((Boolean) result.get("General Partner Name"));
            questionnaireObject.setGeneralPartnerTaxId((Boolean) result.get("General Partner Tax ID"));
            questionnaireObject.setStateOfIncorporation((Boolean) result.get("State Of Incorporation"));
            questionnaireObject.setAnnualSpend((Boolean)result.get("annualSpend"));
            questionnaireObject.setCardPurchaseType((Boolean)result.get("cardPurchaseType"));

            return new ObjectMapper().writeValueAsString(questionnaireObject);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GET
    @Path("/productType/{productType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSubProductTypes(@javax.ws.rs.PathParam("productType") String productType) {

        try {

            String jsonReq = "{\"Product Type\":\"" + productType + "\"}";
            System.out.println(jsonReq);
            String response = processService.getSubProductTypes(jsonReq);


            HashMap mapResponse = new ObjectMapper().readValue(response, HashMap.class);
            List results = (List) mapResponse.get("decisionResults");

            Map resultListVal = (Map) results.get(0);

            List fields = (List) resultListVal.get("result");

            System.out.println(fields);



            return new ObjectMapper().writeValueAsString(fields);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GET
    @Path("/steps/productType/{productType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSteps(@javax.ws.rs.PathParam("productType") String productType) {

        try {

            String jsonReq = "{\"Product Type\":\"" + productType + "\"}";
            System.out.println(jsonReq);
            String response = processService.getSteps(jsonReq);


            HashMap mapResponse = new ObjectMapper().readValue(response, HashMap.class);
            List results = (List) mapResponse.get("decisionResults");

            Map resultListVal = (Map) results.get(0);

            List fields = (List) resultListVal.get("result");

            System.out.println(fields);



            return new ObjectMapper().writeValueAsString(fields);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String postCase(String body) {
        System.out.println("here"+"{\"case-data\":"+body+"}");
        String caseId = processService.postCase("{\"case-data\":"+body+"}");
        System.out.println("id"+caseId);
        return caseId;
    }

    @GET
    @Path("/tasks/{caseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTasks(@PathParam("caseId") String caseId) throws Exception{
        String response = processService.getCustomerTasks(caseId,"customer");

        Map map = new ObjectMapper().readValue(response,Map.class);
        List taskList = (List) map.get("task-summary");
        Map taskMap = (Map) taskList.get(0);
        TaskSummary taskSummary = new TaskSummary();
        taskSummary.setTaskName((String) taskMap.get("task-name"));
        taskSummary.setTaskStatus((String) taskMap.get("task-status"));
        taskSummary.setTaskId((Integer) taskMap.get("task-id"));

        return new ObjectMapper().writeValueAsString(taskSummary);

    }

    @GET
    @Path("/docReq/{caseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDocReq(@PathParam("caseId") String caseId) throws Exception{
        String response = processService.getDocRequirements(caseId);
        List lst= new ObjectMapper().readValue(response,List.class);
        List<DocReq> docs = new ArrayList<>();
        DocReq docReq = null;
        int i= 0;
        for(Object str:lst) {
            i++;
            docReq = new DocReq();
            docReq.setDocId(i);
            docReq.setDocName((String) str);
            docs.add(docReq);
        }
        return new ObjectMapper().writeValueAsString(docs);

    }

    @POST
    @Path("/customer-case/documents/{caseId}/task/{taskId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void postCaseDocs(MultipartFormDataInput dataInput, @javax.ws.rs.PathParam("caseId") String caseId, @PathParam("taskId") String taskId) throws Exception {
        Map<String, List<InputPart>> uploadForm = dataInput.getFormDataMap();

        List<String> docIds = new ArrayList<>();

        String objBegin = "{\"docsReqd\":[";

        List<InputPart> docs = uploadForm.get("uploadedFile");

        System.out.println(docs.size());

        for (InputPart inputPart : docs) {
            int i = 0;

            InputStream inputStream = inputPart.getBody(InputStream.class, null);

            String fileName = getFileName(inputPart.getHeaders());


            if (!objBegin.endsWith("[")) {

                objBegin += ",";
            }
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(bytes, true));

            String docMgmSystemUpdate = "{\n" +

                    "\"document-name\" : \""+fileName+"\",\n" +
                    "  \"document-link\" : null,\n" +
                    "  \"document-size\" : 17,\n" +
                    "  \"document-last-mod\" : {\n" +
                    "    \"java.util.Date\" : 1539936629148\n" +
                    "  },\n" +
                    "  \"document-content\" : \""+base64+"\"\n" +
                    "}";

            String id = processService.addDoc(docMgmSystemUpdate);
            System.out.println(id);
            docIds.add(id);

        }
        processService.addCaseFile(new ObjectMapper().writeValueAsString(docIds),caseId);

        try {
            System.out.println(taskId);
            processService.startTask(taskId, "customer");
            processService.completeTask(taskId, null, "customer");
        }catch (Exception e) {
            e.printStackTrace();
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

    @GET
    @Path("/admin/cases")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCases() throws Exception{
        String response = processService.getCaseDetails();
        List<CaseDetails> cases = new ArrayList<>();
        CaseDetails caseDetails =null;
        Map caseMap = null;
        Map map = new ObjectMapper().readValue(response,Map.class);
        List caseList = (List) map.get("instances");
        for(Object str: caseList) {
          caseMap = (Map) str;
          caseDetails = new CaseDetails();
          caseDetails.setCaseId((String) caseMap.get("case-id"));
          if((int)caseMap.get("case-status") == 1) {
              caseDetails.setCaseStatus("Active");
          } else {
              caseDetails.setCaseStatus("Completed");
          }

            Date date=new Date((long)caseMap.get("case-started-at"));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String requiredDate = df.format(date).toString();
          caseDetails.setCaseStartedAt(requiredDate);
          cases.add(caseDetails);


        }
        return new ObjectMapper().writeValueAsString(cases);

    }
    @GET
    @Path("/admin/caseFile/{caseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCaseFile(@PathParam("caseId") String caseId) throws Exception{
        String response = processService.getCaseFile(caseId);

        Map map = new ObjectMapper().readValue(response,Map.class);
        System.out.println(map);
        return new ObjectMapper().writeValueAsString(map);

    }




}
