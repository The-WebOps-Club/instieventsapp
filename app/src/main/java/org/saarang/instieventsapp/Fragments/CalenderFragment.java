package org.saarang.instieventsapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.instieventsapp.R;

/**
 * Created by Seetharaman on 02-08-2015.
 */
public class CalenderFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_calender, container, false);


        return rootView;
    }
}
