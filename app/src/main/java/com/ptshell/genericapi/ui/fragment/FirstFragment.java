package com.ptshell.genericapi.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptshell.genericapi.R;

public class FirstFragment extends BaseFragment {

    private View view;

    public static FirstFragment getInstance() {
        FirstFragment manualFragment = new FirstFragment();
        return manualFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intiView();
        initData();
    }

    private void intiView() {
    }

    private void initData() {
    }

}
