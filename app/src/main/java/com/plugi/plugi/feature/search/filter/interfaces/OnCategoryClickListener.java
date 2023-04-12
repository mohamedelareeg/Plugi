package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.MainCategory;
import com.plugi.plugi.models.SortIDs;

import java.io.Serializable;


public interface OnCategoryClickListener extends Serializable {
    void onCategoryClicked(MainCategory.Category contact, int position);

}
