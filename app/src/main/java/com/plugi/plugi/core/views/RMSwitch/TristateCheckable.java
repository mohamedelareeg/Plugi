package com.plugi.plugi.core.views.RMSwitch;




public interface TristateCheckable {
    void toggle();

    void setState(@RMTristateSwitch.State int state);

    @RMTristateSwitch.State
    int getState();
}
