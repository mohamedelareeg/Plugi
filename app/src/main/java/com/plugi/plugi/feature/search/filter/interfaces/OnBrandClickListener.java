package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.FilterIDs;
import com.plugi.plugi.models.MainCategory;

import java.io.Serializable;


public interface OnBrandClickListener extends Serializable {
    void onBrandClicked(FilterIDs.BrandsList contact, int position);

}
