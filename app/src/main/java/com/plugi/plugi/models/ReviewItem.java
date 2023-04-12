package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plugi.plugi.models.itemDetails.AllItemSize;
import com.plugi.plugi.models.itemDetails.ExpirationDaysList;
import com.plugi.plugi.models.itemDetails.ItemDetail;
import com.plugi.plugi.models.itemDetails.Proptety;
import com.plugi.plugi.models.itemDetails.RestrictedCountriesToBuyItem;
import com.plugi.plugi.models.itemDetails.ShippingCostPerCountry;
import com.plugi.plugi.models.profile.CustomerPaymentCard;
import com.plugi.plugi.models.profile.CustomerShippingAddress;

import java.io.Serializable;
import java.util.List;

public class ReviewItem implements Serializable {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ItemID")
    @Expose
    private Integer itemID;
    @SerializedName("CustomerID")
    @Expose
    private Integer customerID;
    @SerializedName("CountryID")
    @Expose
    private Integer countryID;
    @SerializedName("itemDetail")
    @Expose
    private ItemDetail itemDetail;
    @SerializedName("UnitSizeType")
    @Expose
    private String unitSizeType;
    @SerializedName("GenderSizeType")
    @Expose
    private String genderSizeType;
    @SerializedName("DefaultSizeValue")
    @Expose
    private String defaultSizeValue;
    @SerializedName("ColorName")
    @Expose
    private String colorName;
    @SerializedName("MinValue_BidPrice")
    @Expose
    private Double minValueBidPrice;
    @SerializedName("MinValue_AskPrice")
    @Expose
    private Double minValueAskPrice;
    @SerializedName("PlugiProcessingFees")
    @Expose
    private Double plugiProcessingFees;
    @SerializedName("TaxFees")
    @Expose
    private Double taxFees;
    @SerializedName("TransactionFees")
    @Expose
    private Double transactionFees;
    @SerializedName("PaymentFees")
    @Expose
    private Double paymentFees;
    @SerializedName("DiscountID")
    @Expose
    private Integer discountID;
    @SerializedName("DiscountCost")
    @Expose
    private Double discountCost;
    @SerializedName("ShippingFees")
    @Expose
    private Double shippingFees;
    @SerializedName("IsCountry_Restricted")
    @Expose
    private Boolean isCountryRestricted;
    @SerializedName("RestrictedCountries_ToBuyItem")
    @Expose
    private List<RestrictedCountriesToBuyItem> restrictedCountriesToBuyItem = null;
    @SerializedName("AllItemSizes")
    @Expose
    private List<AllItemSize> allItemSizes = null;
    @SerializedName("Proprieties")
    @Expose
    private List<Proptety> proprieties = null;
    @SerializedName("CustomerShippingAddress")
    @Expose
    private List<CustomerShippingAddress> customerShippingAddress = null;
    @SerializedName("CustomerPaymentCards")
    @Expose
    private List<CustomerPaymentCard> customerPaymentCards = null;
    @SerializedName("ShippingCost_PerCountries")
    @Expose
    private List<ShippingCostPerCountry> shippingCostPerCountries = null;
    @SerializedName("DefaultExpiryDay")
    @Expose
    private Integer defaultExpiryDay;
    @SerializedName("ExpirationDaysList")
    @Expose
    private List<ExpirationDaysList> expirationDaysList = null;
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

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getUnitSizeType() {
        return unitSizeType;
    }

    public void setUnitSizeType(String unitSizeType) {
        this.unitSizeType = unitSizeType;
    }

    public String getGenderSizeType() {
        return genderSizeType;
    }

    public void setGenderSizeType(String genderSizeType) {
        this.genderSizeType = genderSizeType;
    }

    public String getDefaultSizeValue() {
        return defaultSizeValue;
    }

    public void setDefaultSizeValue(String defaultSizeValue) {
        this.defaultSizeValue = defaultSizeValue;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Double getMinValueBidPrice() {
        return minValueBidPrice;
    }

    public void setMinValueBidPrice(Double minValueBidPrice) {
        this.minValueBidPrice = minValueBidPrice;
    }

    public Double getMinValueAskPrice() {
        return minValueAskPrice;
    }

    public void setMinValueAskPrice(Double minValueAskPrice) {
        this.minValueAskPrice = minValueAskPrice;
    }

    public Double getPlugiProcessingFees() {
        return plugiProcessingFees;
    }

    public void setPlugiProcessingFees(Double plugiProcessingFees) {
        this.plugiProcessingFees = plugiProcessingFees;
    }

    public Double getTaxFees() {
        return taxFees;
    }

    public void setTaxFees(Double taxFees) {
        this.taxFees = taxFees;
    }

    public Double getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Double transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Double getPaymentFees() {
        return paymentFees;
    }

    public void setPaymentFees(Double paymentFees) {
        this.paymentFees = paymentFees;
    }

    public Integer getDiscountID() {
        return discountID;
    }

    public void setDiscountID(Integer discountID) {
        this.discountID = discountID;
    }

    public Double getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(Double discountCost) {
        this.discountCost = discountCost;
    }

    public Double getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(Double shippingFees) {
        this.shippingFees = shippingFees;
    }

    public Boolean getIsCountryRestricted() {
        return isCountryRestricted;
    }

    public void setIsCountryRestricted(Boolean isCountryRestricted) {
        this.isCountryRestricted = isCountryRestricted;
    }

    public List<RestrictedCountriesToBuyItem> getRestrictedCountriesToBuyItem() {
        return restrictedCountriesToBuyItem;
    }

    public void setRestrictedCountriesToBuyItem(List<RestrictedCountriesToBuyItem> restrictedCountriesToBuyItem) {
        this.restrictedCountriesToBuyItem = restrictedCountriesToBuyItem;
    }

    public List<AllItemSize> getAllItemSizes() {
        return allItemSizes;
    }

    public void setAllItemSizes(List<AllItemSize> allItemSizes) {
        this.allItemSizes = allItemSizes;
    }

    public List<Proptety> getProprieties() {
        return proprieties;
    }

    public void setProprieties(List<Proptety> proprieties) {
        this.proprieties = proprieties;
    }

    public List<CustomerShippingAddress> getCustomerShippingAddress() {
        return customerShippingAddress;
    }

    public void setCustomerShippingAddress(List<CustomerShippingAddress> customerShippingAddress) {
        this.customerShippingAddress = customerShippingAddress;
    }

    public List<CustomerPaymentCard> getCustomerPaymentCards() {
        return customerPaymentCards;
    }

    public void setCustomerPaymentCards(List<CustomerPaymentCard> customerPaymentCards) {
        this.customerPaymentCards = customerPaymentCards;
    }

    public List<ShippingCostPerCountry> getShippingCostPerCountries() {
        return shippingCostPerCountries;
    }

    public void setShippingCostPerCountries(List<ShippingCostPerCountry> shippingCostPerCountries) {
        this.shippingCostPerCountries = shippingCostPerCountries;
    }

    public Integer getDefaultExpiryDay() {
        return defaultExpiryDay;
    }

    public void setDefaultExpiryDay(Integer defaultExpiryDay) {
        this.defaultExpiryDay = defaultExpiryDay;
    }

    public List<ExpirationDaysList> getExpirationDaysList() {
        return expirationDaysList;
    }

    public void setExpirationDaysList(List<ExpirationDaysList> expirationDaysList) {
        this.expirationDaysList = expirationDaysList;
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
