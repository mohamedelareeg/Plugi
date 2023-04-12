package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetDiscountDetails implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("DiscountCode")
    @Expose
    private String discountCode;
    @SerializedName("Discount_Detail")
    @Expose
    private DiscountDetail discountDetail;
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

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public DiscountDetail getDiscountDetail() {
        return discountDetail;
    }

    public void setDiscountDetail(DiscountDetail discountDetail) {
        this.discountDetail = discountDetail;
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
    public class DiscountDetail implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("DiscountID")
        @Expose
        private Integer discountID;
        @SerializedName("DiscountCode")
        @Expose
        private String discountCode;
        @SerializedName("DiscountCost")
        @Expose
        private Double discountCost;
        @SerializedName("PriceCurrency")
        @Expose
        private String priceCurrency;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getDiscountID() {
            return discountID;
        }

        public void setDiscountID(Integer discountID) {
            this.discountID = discountID;
        }

        public String getDiscountCode() {
            return discountCode;
        }

        public void setDiscountCode(String discountCode) {
            this.discountCode = discountCode;
        }

        public Double getDiscountCost() {
            return discountCost;
        }

        public void setDiscountCost(Double discountCost) {
            this.discountCost = discountCost;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

    }

}
