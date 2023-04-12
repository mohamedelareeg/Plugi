package com.plugi.plugi.feature.profile.buyingselling.interfaces;


import com.plugi.plugi.models.orderDetails.HistoryList;

import java.io.Serializable;


public interface OnHistoryClickListener extends Serializable {
    void onHistoryClicked(HistoryList contact, int position);

}
