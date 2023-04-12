package com.plugi.plugi.core.views.SlidingTab;



public interface IFTabAdapter {

    String getTitle(int position);

    int getIcon(int position);

    boolean isEnableBadge(int position);
}
