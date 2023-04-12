package com.plugi.plugi.feature.profile.buyingselling.interfaces;


import com.plugi.plugi.models.orderDetails.CurrentList;

import java.io.Serializable;


public interface OnCurrentClickListener extends Serializable {
    void onCurrentClicked(CurrentList contact, int position);

}
