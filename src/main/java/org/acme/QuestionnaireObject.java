package org.acme;

public class QuestionnaireObject {
    boolean generalPartnerName;
    boolean stateOfIncorporation;
    boolean generalPartnerTaxId;
    boolean annualSpend;
    boolean cardPurchaseType;

    public boolean isAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(boolean annualSpend) {
        this.annualSpend = annualSpend;
    }

    public boolean isCardPurchaseType() {
        return cardPurchaseType;
    }

    public void setCardPurchaseType(boolean cardPurchaseType) {
        this.cardPurchaseType = cardPurchaseType;
    }

    public boolean isGeneralPartnerName() {
        return generalPartnerName;
    }

    public void setGeneralPartnerName(boolean generalPartnerName) {
        this.generalPartnerName = generalPartnerName;
    }

    public boolean isStateOfIncorporation() {
        return stateOfIncorporation;
    }

    public void setStateOfIncorporation(boolean stateOfIncorporation) {
        this.stateOfIncorporation = stateOfIncorporation;
    }

    public boolean isGeneralPartnerTaxId() {
        return generalPartnerTaxId;
    }

    public void setGeneralPartnerTaxId(boolean generalPartnerTaxId) {
        this.generalPartnerTaxId = generalPartnerTaxId;
    }
}
