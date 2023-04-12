package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.FilterIDs;

import java.io.Serializable;


public interface OnReleaseYearClickListener extends Serializable {
    void onReleaseYearClicked(FilterIDs.ReleaseYearList contact, int position);

}
