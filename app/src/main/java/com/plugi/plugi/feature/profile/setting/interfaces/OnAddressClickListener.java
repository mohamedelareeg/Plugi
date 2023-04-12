package com.plugi.plugi.feature.profile.setting.interfaces;


import com.plugi.plugi.models.CustomerAddress;

import java.io.Serializable;


public interface OnAddressClickListener extends Serializable {
    void onAddressClicked(CustomerAddress contact, int position);

}
