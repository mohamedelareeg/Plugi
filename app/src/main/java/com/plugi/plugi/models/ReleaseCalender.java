package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReleaseCalender implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("BrandId")
    @Expose
    private Integer brandId;
    @SerializedName("StatusID")
    @Expose
    private Integer statusID;
    @SerializedName("SearchKey")
    @Expose
    private Object searchKey;
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

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public Object getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(Object searchKey) {
        this.searchKey = searchKey;
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
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("MainImage")
        @Expose
        private String mainImage;
        @SerializedName("Name_AR")
        @Expose
        private String nameAR;
        @SerializedName("Name_EN")
        @Expose
        private String nameEN;
        @SerializedName("Description_AR")
        @Expose
        private String descriptionAR;
        @SerializedName("Description_EN")
        @Expose
        private String descriptionEN;
        @SerializedName("RetialPrice")
        @Expose
        private Double retialPrice;
        @SerializedName("ReleaseDate")
        @Expose
        private String releaseDate;
        @SerializedName("Condition")
        @Expose
        private String condition;
        @SerializedName("MaxBidPrice")
        @Expose
        private Object maxBidPrice;
        @SerializedName("LowAskPrice")
        @Expose
        private Object lowAskPrice;
        @SerializedName("TotalSold")
        @Expose
        private Integer totalSold;
        @SerializedName("LastSoldDate")
        @Expose
        private String lastSoldDate;
        @SerializedName("CategoryID")
        @Expose
        private Integer categoryID;
        @SerializedName("Category_Name")
        @Expose
        private String categoryName;
        @SerializedName("BrandCat_ID")
        @Expose
        private Integer brandCatID;
        @SerializedName("BrandCat_Name")
        @Expose
        private String brandCatName;
        @SerializedName("SubBrandCat_ID")
        @Expose
        private Integer subBrandCatID;
        @SerializedName("SubBrandCat_Name")
        @Expose
        private String subBrandCatName;
        @SerializedName("currency_ID")
        @Expose
        private Integer currencyID;
        @SerializedName("currency_Name")
        @Expose
        private String currencyName;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("Status_name")
        @Expose
        private String statusName;
        @SerializedName("Sort_Order")
        @Expose
        private Integer sortOrder;
        @SerializedName("ItemImages")
        @Expose
        private Object itemImages;
        @SerializedName("ItemColors")
        @Expose
        private List<Object> itemColors = null;
        @SerializedName("ItemSizes")
        @Expose
        private Object itemSizes;
        @SerializedName("Propteties")
        @Expose
        private Object propteties;

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

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public String getNameAR() {
            return nameAR;
        }

        public void setNameAR(String nameAR) {
            this.nameAR = nameAR;
        }

        public String getNameEN() {
            return nameEN;
        }

        public void setNameEN(String nameEN) {
            this.nameEN = nameEN;
        }

        public String getDescriptionAR() {
            return descriptionAR;
        }

        public void setDescriptionAR(String descriptionAR) {
            this.descriptionAR = descriptionAR;
        }

        public String getDescriptionEN() {
            return descriptionEN;
        }

        public void setDescriptionEN(String descriptionEN) {
            this.descriptionEN = descriptionEN;
        }

        public Double getRetialPrice() {
            return retialPrice;
        }

        public void setRetialPrice(Double retialPrice) {
            this.retialPrice = retialPrice;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public Object getMaxBidPrice() {
            return maxBidPrice;
        }

        public void setMaxBidPrice(Object maxBidPrice) {
            this.maxBidPrice = maxBidPrice;
        }

        public Object getLowAskPrice() {
            return lowAskPrice;
        }

        public void setLowAskPrice(Object lowAskPrice) {
            this.lowAskPrice = lowAskPrice;
        }

        public Integer getTotalSold() {
            return totalSold;
        }

        public void setTotalSold(Integer totalSold) {
            this.totalSold = totalSold;
        }

        public String getLastSoldDate() {
            return lastSoldDate;
        }

        public void setLastSoldDate(String lastSoldDate) {
            this.lastSoldDate = lastSoldDate;
        }

        public Integer getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(Integer categoryID) {
            this.categoryID = categoryID;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getBrandCatID() {
            return brandCatID;
        }

        public void setBrandCatID(Integer brandCatID) {
            this.brandCatID = brandCatID;
        }

        public String getBrandCatName() {
            return brandCatName;
        }

        public void setBrandCatName(String brandCatName) {
            this.brandCatName = brandCatName;
        }

        public Integer getSubBrandCatID() {
            return subBrandCatID;
        }

        public void setSubBrandCatID(Integer subBrandCatID) {
            this.subBrandCatID = subBrandCatID;
        }

        public String getSubBrandCatName() {
            return subBrandCatName;
        }

        public void setSubBrandCatName(String subBrandCatName) {
            this.subBrandCatName = subBrandCatName;
        }

        public Integer getCurrencyID() {
            return currencyID;
        }

        public void setCurrencyID(Integer currencyID) {
            this.currencyID = currencyID;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public Integer getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(Integer sortOrder) {
            this.sortOrder = sortOrder;
        }

        public Object getItemImages() {
            return itemImages;
        }

        public void setItemImages(Object itemImages) {
            this.itemImages = itemImages;
        }

        public List<Object> getItemColors() {
            return itemColors;
        }

        public void setItemColors(List<Object> itemColors) {
            this.itemColors = itemColors;
        }

        public Object getItemSizes() {
            return itemSizes;
        }

        public void setItemSizes(Object itemSizes) {
            this.itemSizes = itemSizes;
        }

        public Object getPropteties() {
            return propteties;
        }

        public void setPropteties(Object propteties) {
            this.propteties = propteties;
        }

    }

}
