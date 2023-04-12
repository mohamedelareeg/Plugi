package com.plugi.plugi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Blog implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("BlogID")
    @Expose
    private Integer blogID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Video_URL")
    @Expose
    private String videoURL;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Sort_Order")
    @Expose
    private Integer sortOrder;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getBlogID() {
        return blogID;
    }

    public void setBlogID(Integer blogID) {
        this.blogID = blogID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
