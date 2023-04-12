package com.plugi.plugi.feature.main.interfaces;


import com.plugi.plugi.models.SocialMedia;


import java.io.Serializable;


public interface OnSocialClickListener extends Serializable {
    void onSocialClicked(SocialMedia.Link contact, int position);

}
