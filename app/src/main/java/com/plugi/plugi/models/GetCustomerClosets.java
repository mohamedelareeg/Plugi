package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCustomerClosets {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Customer_ID")
    @Expose
    private Integer customerID;
    @SerializedName("TotalCloset")
    @Expose
    private Integer totalCloset;
    @SerializedName("TotalGain_Loss")
    @Expose
    private Double totalGainLoss;
    @SerializedName("PriceCurrency")
    @Expose
    private String priceCurrency;
    @SerializedName("ClosetDetails")
    @Expose
    private List<ClosetDetail> closetDetails = null;
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

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getTotalCloset() {
        return totalCloset;
    }

    public void setTotalCloset(Integer totalCloset) {
        this.totalCloset = totalCloset;
    }

    public Double getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(Double totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public List<ClosetDetail> getClosetDetails() {
        return closetDetails;
    }

    public void setClosetDetails(List<ClosetDetail> closetDetails) {
        this.closetDetails = closetDetails;
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

    public class ClosetDetail implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("ClosetId")
        @Expose
        private Integer closetId;
        @SerializedName("ItemID")
        @Expose
        private Integer itemID;
        @SerializedName("itemImage")
        @Expose
        private String itemImage;
        @SerializedName("ItemName")
        @Expose
        private String itemName;
        @SerializedName("SizeId")
        @Expose
        private Integer sizeId;
        @SerializedName("ConditionId")
        @Expose
        private Integer conditionId;
        @SerializedName("Sizevalue")
        @Expose
        private String sizevalue;
        @SerializedName("ConditionValue")
        @Expose
        private String conditionValue;
        @SerializedName("Colorvalue")
        @Expose
        private String colorvalue;
        @SerializedName("PurchaseDate")
        @Expose
        private String purchaseDate;
        @SerializedName("PurchasePrice")
        @Expose
        private Double purchasePrice;
        @SerializedName("LowestAskPrice")
        @Expose
        private Double lowestAskPrice;
        @SerializedName("GainOrLoss")
        @Expose
        private Double gainOrLoss;
        @SerializedName("PriceCurrency")
        @Expose
        private String priceCurrency;

        private boolean selected;
        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getClosetId() {
            return closetId;
        }

        public void setClosetId(Integer closetId) {
            this.closetId = closetId;
        }

        public Integer getItemID() {
            return itemID;
        }

        public void setItemID(Integer itemID) {
            this.itemID = itemID;
        }

        public String getItemImage() {
            return itemImage;
        }

        public void setItemImage(String itemImage) {
            this.itemImage = itemImage;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Integer getSizeId() {
            return sizeId;
        }

        public void setSizeId(Integer sizeId) {
            this.sizeId = sizeId;
        }

        public Integer getConditionId() {
            return conditionId;
        }

        public void setConditionId(Integer conditionId) {
            this.conditionId = conditionId;
        }

        public String getSizevalue() {
            return sizevalue;
        }

        public void setSizevalue(String sizevalue) {
            this.sizevalue = sizevalue;
        }

        public String getConditionValue() {
            return conditionValue;
        }

        public void setConditionValue(String conditionValue) {
            this.conditionValue = conditionValue;
        }

        public String getColorvalue() {
            return colorvalue;
        }

        public void setColorvalue(String colorvalue) {
            this.colorvalue = colorvalue;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public Double getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(Double purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public Double getLowestAskPrice() {
            return lowestAskPrice;
        }

        public void setLowestAskPrice(Double lowestAskPrice) {
            this.lowestAskPrice = lowestAskPrice;
        }

        public Double getGainOrLoss() {
            return gainOrLoss;
        }

        public void setGainOrLoss(Double gainOrLoss) {
            this.gainOrLoss = gainOrLoss;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

}
