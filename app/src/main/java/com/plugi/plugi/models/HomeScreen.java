package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeScreen implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("CategoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("Brands")
    @Expose
    private List<Brand> brands = null;
    @SerializedName("MostPoPularItems")
    @Expose
    private List<MostPoPularItem> mostPoPularItems = null;
    @SerializedName("ReleaseCalenderItems")
    @Expose
    private List<ReleaseCalenderItem> releaseCalenderItems = null;
    @SerializedName("LowestASKsItems")
    @Expose
    private List<LowestASKsItem> lowestASKsItems = null;
    @SerializedName("HightestBidsItems")
    @Expose
    private List<HightestBidsItem> hightestBidsItems = null;
    @SerializedName("BannerImage")
    @Expose
    private String bannerImage;
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

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<MostPoPularItem> getMostPoPularItems() {
        return mostPoPularItems;
    }

    public void setMostPoPularItems(List<MostPoPularItem> mostPoPularItems) {
        this.mostPoPularItems = mostPoPularItems;
    }

    public List<ReleaseCalenderItem> getReleaseCalenderItems() {
        return releaseCalenderItems;
    }

    public void setReleaseCalenderItems(List<ReleaseCalenderItem> releaseCalenderItems) {
        this.releaseCalenderItems = releaseCalenderItems;
    }

    public List<LowestASKsItem> getLowestASKsItems() {
        return lowestASKsItems;
    }

    public void setLowestASKsItems(List<LowestASKsItem> lowestASKsItems) {
        this.lowestASKsItems = lowestASKsItems;
    }

    public List<HightestBidsItem> getHightestBidsItems() {
        return hightestBidsItems;
    }

    public void setHightestBidsItems(List<HightestBidsItem> hightestBidsItems) {
        this.hightestBidsItems = hightestBidsItems;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
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
    public class LowestASKsItem implements Serializable {

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
    public class MostPoPularItem implements Serializable{

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
    public class ReleaseCalenderItem implements Serializable {

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
    public class Brand implements Serializable {

        @SerializedName("$id")
        @Expose
        private String $id;
        @SerializedName("Category_ID")
        @Expose
        private Integer categoryID;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Parent_ID")
        @Expose
        private Integer parentID;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getParentID() {
            return parentID;
        }

        public void setParentID(Integer parentID) {
            this.parentID = parentID;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

    }
    public class HightestBidsItem implements Serializable {

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
