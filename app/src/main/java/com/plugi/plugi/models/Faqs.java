package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Faqs implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("FAQItems")
    @Expose
    private List<FAQItem> fAQItems = null;
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
    private String statusMessage;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public List<FAQItem> getFAQItems() {
        return fAQItems;
    }

    public void setFAQItems(List<FAQItem> fAQItems) {
        this.fAQItems = fAQItems;
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public class FAQItem implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("Answer")
        @Expose
        private String answer;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

    }
}