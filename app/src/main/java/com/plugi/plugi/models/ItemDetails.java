package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plugi.plugi.models.itemDetails.AllItemSize;
import com.plugi.plugi.models.itemDetails.AllSale;
import com.plugi.plugi.models.itemDetails.AllitemASK;
import com.plugi.plugi.models.itemDetails.AllitemBid;
import com.plugi.plugi.models.itemDetails.ItemDetail;
import com.plugi.plugi.models.itemDetails.ItemImage;
import com.plugi.plugi.models.itemDetails.LastSale;
import com.plugi.plugi.models.itemDetails.Proptety;
import com.plugi.plugi.models.itemDetails.Relateditem;

import java.io.Serializable;
import java.util.List;

public class ItemDetails implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ItemID")
    @Expose
    private Integer itemID;
    @SerializedName("itemDetail")
    @Expose
    private ItemDetail itemDetail;
    @SerializedName("Propteties")
    @Expose
    private List<Proptety> propteties = null;
    @SerializedName("Relateditems")
    @Expose
    private List<Relateditem> relateditems = null;
    @SerializedName("LastSales")
    @Expose
    private List<LastSale> lastSales = null;
    @SerializedName("AllSales")
    @Expose
    private List<AllSale> allSales = null;
    @SerializedName("AllItemSizes")
    @Expose
    private List<AllItemSize> allItemSizes = null;
    @SerializedName("AllitemASKs")
    @Expose
    private List<AllitemASK> allitemASKs = null;
    @SerializedName("AllitemBids")
    @Expose
    private List<AllitemBid> allitemBids = null;
    @SerializedName("SizeChartURL")
    @Expose
    private String sizeChartURL;
    @SerializedName("ItemImages")
    @Expose
    private List<ItemImage> itemImages = null;
    @SerializedName("CustomerFollowStatus")
    @Expose
    private Boolean customerFollowStatus;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
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

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public List<Proptety> getPropteties() {
        return propteties;
    }

    public void setPropteties(List<Proptety> propteties) {
        this.propteties = propteties;
    }

    public List<Relateditem> getRelateditems() {
        return relateditems;
    }

    public void setRelateditems(List<Relateditem> relateditems) {
        this.relateditems = relateditems;
    }

    public List<LastSale> getLastSales() {
        return lastSales;
    }

    public void setLastSales(List<LastSale> lastSales) {
        this.lastSales = lastSales;
    }

    public List<AllSale> getAllSales() {
        return allSales;
    }

    public void setAllSales(List<AllSale> allSales) {
        this.allSales = allSales;
    }

    public List<AllItemSize> getAllItemSizes() {
        return allItemSizes;
    }

    public void setAllItemSizes(List<AllItemSize> allItemSizes) {
        this.allItemSizes = allItemSizes;
    }

    public List<AllitemASK> getAllitemASKs() {
        return allitemASKs;
    }

    public void setAllitemASKs(List<AllitemASK> allitemASKs) {
        this.allitemASKs = allitemASKs;
    }

    public List<AllitemBid> getAllitemBids() {
        return allitemBids;
    }

    public void setAllitemBids(List<AllitemBid> allitemBids) {
        this.allitemBids = allitemBids;
    }

    public String getSizeChartURL() {
        return sizeChartURL;
    }

    public void setSizeChartURL(String sizeChartURL) {
        this.sizeChartURL = sizeChartURL;
    }

    public List<ItemImage> getItemImages() {
        return itemImages;
    }

    public void setItemImages(List<ItemImage> itemImages) {
        this.itemImages = itemImages;
    }

    public Boolean getCustomerFollowStatus() {
        return customerFollowStatus;
    }

    public void setCustomerFollowStatus(Boolean customerFollowStatus) {
        this.customerFollowStatus = customerFollowStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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


}