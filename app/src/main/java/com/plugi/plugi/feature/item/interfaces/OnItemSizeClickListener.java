package com.plugi.plugi.feature.item.interfaces;


import com.plugi.plugi.models.itemDetails.AllItemSize;

import java.io.Serializable;


public interface OnItemSizeClickListener extends Serializable {
    void onItemSizeClicked(AllItemSize contact, int position);

}
