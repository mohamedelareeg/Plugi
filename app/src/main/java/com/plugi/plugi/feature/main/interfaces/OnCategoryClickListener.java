package com.plugi.plugi.feature.main.interfaces;


import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.SocialMedia;

import java.io.Serializable;


public interface OnCategoryClickListener extends Serializable {
    void onCategoryClicked(MainCategory.Category contact, int position);
    void onCategorySortedClicked(MainCategory.Category contact, int position);

}
