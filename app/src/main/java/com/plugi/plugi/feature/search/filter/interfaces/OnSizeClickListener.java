package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.FilterIDs;

import java.io.Serializable;


public interface OnSizeClickListener extends Serializable {
    void onSizeClicked(FilterIDs.SizeList contact, int position);

}
