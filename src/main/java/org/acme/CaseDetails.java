package org.acme;

public class CaseDetails {
    private String caseId;
    private String caseStatus;
    private String caseStartedAt;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseStartedAt() {
        return caseStartedAt;
    }

    public void setCaseStartedAt(String caseStartedAt) {
        this.caseStartedAt = caseStartedAt;
    }
}
