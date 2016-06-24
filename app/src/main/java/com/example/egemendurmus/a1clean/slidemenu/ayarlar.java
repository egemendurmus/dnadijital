package com.example.egemendurmus.a1clean.slidemenu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.egemendurmus.a1clean.R;

public class ayarlar extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getActivity(). setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }
}