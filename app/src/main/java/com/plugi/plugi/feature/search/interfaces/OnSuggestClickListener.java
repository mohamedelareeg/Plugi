package com.plugi.plugi.feature.search.interfaces;


import com.plugi.plugi.models.SocialMedia;

import java.io.Serializable;


public interface OnSuggestClickListener extends Serializable {
    void onSuggestClicked(String contact, int position);

}
