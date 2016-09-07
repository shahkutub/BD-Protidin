package com.kalerkantho.Fragment;

import android.support.v4.app.Fragment;
public interface CommunicatorFragmentInterface {

    public void  setContentFragment(Fragment fragment, boolean addToBackStack);
    public void addContentFragment(Fragment fragment, boolean addToBackStack);
}