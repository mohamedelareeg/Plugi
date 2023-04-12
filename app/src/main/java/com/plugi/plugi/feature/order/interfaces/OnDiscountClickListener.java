package com.plugi.plugi.feature.order.interfaces;


import com.plugi.plugi.models.GetDiscountDetails;

import java.io.Serializable;


public interface OnDiscountClickListener extends Serializable {
    void onDiscountClicked(GetDiscountDetails.DiscountDetail contact, int position);

}
