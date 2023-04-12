package com.plugi.plugi.feature.order.interfaces;


import com.plugi.plugi.models.itemDetails.ExpirationDaysList;

import java.io.Serializable;


public interface OnExpirationClickListener extends Serializable {
    void onExpirationClicked(ExpirationDaysList contact, int position);

}
