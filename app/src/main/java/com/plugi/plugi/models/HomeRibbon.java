package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeRibbon implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Items")
    @Expose
    private List<Item> items = null;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
    public class Item implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("ItemId")
        @Expose
        private Integer itemId;
        @SerializedName("ItemName")
        @Expose
        private String itemName;
        @SerializedName("ItemValue")
        @Expose
        private Double itemValue;
        @SerializedName("UpDown")
        @Expose
        private Integer upDown;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Double getItemValue() {
            return itemValue;
        }

        public void setItemValue(Double itemValue) {
            this.itemValue = itemValue;
        }

        public Integer getUpDown() {
            return upDown;
        }

        public void setUpDown(Integer upDown) {
            this.upDown = upDown;
        }

    }

}
