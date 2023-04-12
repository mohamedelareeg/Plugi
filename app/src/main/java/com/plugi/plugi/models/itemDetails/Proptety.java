package com.plugi.plugi.models.itemDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Proptety implements Serializable {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("PropName")
    @Expose
    private String propName;
    @SerializedName("PropValue")
    @Expose
    private String propValue;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

}
