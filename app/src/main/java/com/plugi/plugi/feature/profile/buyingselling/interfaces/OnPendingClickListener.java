package com.plugi.plugi.feature.profile.buyingselling.interfaces;


import com.plugi.plugi.models.orderDetails.PendingList;

import java.io.Serializable;


public interface OnPendingClickListener extends Serializable {
    void onPendingClicked(PendingList contact, int position);

}
