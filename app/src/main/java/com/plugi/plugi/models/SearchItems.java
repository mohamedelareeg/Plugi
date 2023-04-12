package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchItems implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("Filter_SizeTypeIDs")
    @Expose
    private String filterSizeTypeIDs;
    @SerializedName("Filter_SizeIDs")
    @Expose
    private String filterSizeIDs;
    @SerializedName("Filter_PriceIDs")
    @Expose
    private String filterPriceIDs;
    @SerializedName("Filter_YearIDs")
    @Expose
    private String filterYearIDs;
    @SerializedName("SortByID")
    @Expose
    private Integer sortByID;
    @SerializedName("SearchKey")
    @Expose
    private String searchKey;
    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;
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

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getFilterSizeTypeIDs() {
        return filterSizeTypeIDs;
    }

    public void setFilterSizeTypeIDs(String filterSizeTypeIDs) {
        this.filterSizeTypeIDs = filterSizeTypeIDs;
    }

    public String getFilterSizeIDs() {
        return filterSizeIDs;
    }

    public void setFilterSizeIDs(String filterSizeIDs) {
        this.filterSizeIDs = filterSizeIDs;
    }

    public String getFilterPriceIDs() {
        return filterPriceIDs;
    }

    public void setFilterPriceIDs(String filterPriceIDs) {
        this.filterPriceIDs = filterPriceIDs;
    }

    public String getFilterYearIDs() {
        return filterYearIDs;
    }

    public void setFilterYearIDs(String filterYearIDs) {
        this.filterYearIDs = filterYearIDs;
    }

    public Integer getSortByID() {
        return sortByID;
    }

    public void setSortByID(Integer sortByID) {
        this.sortByID = sortByID;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Description1")
        @Expose
        private String description1;
        @SerializedName("PriceTitle")
        @Expose
        private String priceTitle;
        @SerializedName("PriceValue")
        @Expose
        private Double priceValue;
        @SerializedName("PriceCurrency")
        @Expose
        private String priceCurrency;
        @SerializedName("Description2")
        @Expose
        private String description2;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription1() {
            return description1;
        }

        public void setDescription1(String description1) {
            this.description1 = description1;
        }

        public String getPriceTitle() {
            return priceTitle;
        }

        public void setPriceTitle(String priceTitle) {
            this.priceTitle = priceTitle;
        }

        public Double getPriceValue() {
            return priceValue;
        }

        public void setPriceValue(Double priceValue) {
            this.priceValue = priceValue;
        }

        public String getPriceCurrency() {
            return priceCurrency;
        }

        public void setPriceCurrency(String priceCurrency) {
            this.priceCurrency = priceCurrency;
        }

        public String getDescription2() {
            return description2;
        }

        public void setDescription2(String description2) {
            this.description2 = description2;
        }

    }
}
