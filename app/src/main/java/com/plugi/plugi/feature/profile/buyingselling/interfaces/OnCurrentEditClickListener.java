package com.plugi.plugi.feature.profile.buyingselling.interfaces;


import com.plugi.plugi.models.orderDetails.CurrentList;

import java.io.Serializable;


public interface OnCurrentEditClickListener extends Serializable {
    void onCurrentEditClicked(CurrentList contact, int position);

}
