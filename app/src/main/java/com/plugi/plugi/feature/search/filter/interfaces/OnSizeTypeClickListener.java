package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.FilterIDs;

import java.io.Serializable;


public interface OnSizeTypeClickListener extends Serializable {
    void onSizeTypeClicked(FilterIDs.SizeTypeList contact, int position);

}
