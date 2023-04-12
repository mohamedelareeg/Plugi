package com.plugi.plugi.feature.category.interfaces;


import com.plugi.plugi.models.SocialMedia;

import java.io.Serializable;


public interface OnItemClickListener extends Serializable {
    void onItemClicked(Integer itemID , Integer categoryID);

}
