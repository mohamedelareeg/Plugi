package com.plugi.plugi.feature.profile.interfaces;


import com.plugi.plugi.models.GetCustomerWallet;

import java.io.Serializable;


public interface OnWalletClickListener extends Serializable {
    void onWalletClicked(GetCustomerWallet.Detail contact, int position);

}
