package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.FilterIDs;

import java.io.Serializable;


public interface OnPriceClickListener extends Serializable {
    void onPriceClicked(FilterIDs.PriceList contact, int position);

}
