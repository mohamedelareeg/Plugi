package com.plugi.plugi.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerPaymentCard implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Customer_ID")
    @Expose
    private Integer customerID;
    @SerializedName("Card_Type")
    @Expose
    private Integer cardType;
    @SerializedName("Card_TypeName")
    @Expose
    private String cardTypeName;
    @SerializedName("Card_No")
    @Expose
    private Integer cardNo;
    @SerializedName("Card_CVV")
    @Expose
    private Integer cardCVV;
    @SerializedName("Expiration_Date")
    @Expose
    private String expirationDate;
    @SerializedName("Holding_Name")
    @Expose
    private String holdingName;
    @SerializedName("Language_ID")
    @Expose
    private Integer languageID;
    @SerializedName("Currency_ID")
    @Expose
    private Integer currencyID;
    @SerializedName("Status_Code")
    @Expose
    private Integer statusCode;
    @SerializedName("Status_Message")
    @Expose
    private Object statusMessage;

    public CustomerPaymentCard(String $id, Integer iD, Integer customerID, Integer cardType, String cardTypeName, Integer cardNo, Integer cardCVV, String expirationDate, String holdingName, Integer languageID, Integer currencyID) {
        this.$id = $id;
        this.iD = iD;
        this.customerID = customerID;
        this.cardType = cardType;
        this.cardTypeName = cardTypeName;
        this.cardNo = cardNo;
        this.cardCVV = cardCVV;
        this.expirationDate = expirationDate;
        this.holdingName = holdingName;
        this.languageID = languageID;
        this.currencyID = currencyID;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(Integer cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getHoldingName() {
        return holdingName;
    }

    public void setHoldingName(String holdingName) {
        this.holdingName = holdingName;
    }

    public Integer getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Integer languageID) {
        this.languageID = languageID;
    }

    public Integer getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(Object statusMessage) {
        this.statusMessage = statusMessage;
    }

}
