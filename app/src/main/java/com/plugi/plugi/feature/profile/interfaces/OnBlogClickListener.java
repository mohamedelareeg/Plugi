package com.plugi.plugi.feature.profile.interfaces;


import com.plugi.plugi.models.Blog;

import java.io.Serializable;


public interface OnBlogClickListener extends Serializable {
    void onBlogClicked(Blog contact, int position);

}
