package com.plugi.plugi.feature.order.interfaces;


import com.plugi.plugi.models.profile.CustomerShippingAddress;

import java.io.Serializable;


public interface OnAddressClickListener extends Serializable {
    void onAddressClicked(CustomerShippingAddress contact, int position);

}
