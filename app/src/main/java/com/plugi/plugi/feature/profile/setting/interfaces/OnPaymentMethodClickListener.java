package com.plugi.plugi.feature.profile.setting.interfaces;


import com.plugi.plugi.models.CustomerCard;

import java.io.Serializable;


public interface OnPaymentMethodClickListener extends Serializable {
    void onPaymentMethodClicked(CustomerCard contact, int position);

}
