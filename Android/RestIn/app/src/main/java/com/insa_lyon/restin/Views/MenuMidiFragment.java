package com.insa_lyon.restin.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.insa_lyon.restin.R;

/**
 * Created by Pierre on 08/01/2017.
 */

public class MenuMidiFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lunch_layout, container, false);
    }
}