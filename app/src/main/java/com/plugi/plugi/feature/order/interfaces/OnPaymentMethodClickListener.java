package com.plugi.plugi.feature.order.interfaces;


import com.plugi.plugi.models.profile.CustomerPaymentCard;

import java.io.Serializable;


public interface OnPaymentMethodClickListener extends Serializable {
    void onPaymentMethodClicked(CustomerPaymentCard contact, int position);

}
