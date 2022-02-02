package org.acme;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

@RegisterRestClient
public interface ProcessService {

    @POST
    @Path("/server/containers/commercial-onboarding_1.0.0-SNAPSHOT/dmn/models/QuestionnaireRules/dmnresult")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getQuestionnaireRules(String body);

    @POST
    @Path("/server/containers/commercial-onboarding_1.0.0-SNAPSHOT/dmn/models/ProductTypeDecision/dmnresult")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getSubProductTypes(String body);

    @POST
    @Path("/server/containers/commercial-onboarding_1.0.0-SNAPSHOT/dmn/models/ProcessStepsDMN/dmnresult")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getSteps(String body);

    @POST
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/cases/customer-onboarding-case.OnboardingCase/instances")
    @Produces("application/json")
    @Consumes("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String postCase(String body);

    @GET
    @Path("/server/queries/cases/instances/{caseId}/tasks/instances/pot-owners")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getCustomerTasks(@PathParam("caseId") String caseId, @QueryParam("user") String user);

    @GET
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/cases/instances/{caseId}/caseFile/DocumentChecklist")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getDocRequirements(@PathParam("caseId") String caseId);

    @POST
    @Path("/server/documents")
    @Produces("application/json")
    @Consumes("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String addDoc(String body);

    @POST
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/cases/instances/{caseId}/caseFile/docIds")
    @Produces("application/json")
    @Consumes("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String addCaseFile(String body, @PathParam("caseId") String caseId);


    @PUT
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/tasks/{taskId}/states/completed")
    @Consumes("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String completeTask(@javax.ws.rs.PathParam("taskId") String taskId, String body, @QueryParam("user") String user);

    @PUT
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/tasks/{taskId}/states/started")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String startTask(@javax.ws.rs.PathParam("taskId") String taskId, @QueryParam("user") String user);

    @GET
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/cases/instances")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getCaseDetails();

    @GET
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/cases/instances/{caseId}/caseFile")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getCaseFile(@PathParam("caseId") String caseId);

    @GET
    @Path("/server/containers/customer-onboarding-case_1.0.0-SNAPSHOT/cases/instances/{caseId}")
    @Produces("application/json")
    @ClientHeaderParam(name="Authorization", value="Basic cGFtQWRtaW46cmVkaGF0cGFtMSE=")
    String getProcessInstance(@PathParam("caseId") String caseId);








}