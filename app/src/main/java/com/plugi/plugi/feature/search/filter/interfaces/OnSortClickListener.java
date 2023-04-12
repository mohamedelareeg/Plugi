package com.plugi.plugi.feature.search.filter.interfaces;


import com.plugi.plugi.models.SortIDs;

import java.io.Serializable;


public interface OnSortClickListener extends Serializable {
    void onSortClicked(SortIDs.SortListID contact, int position);

}
