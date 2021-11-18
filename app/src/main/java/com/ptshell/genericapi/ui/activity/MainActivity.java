package com.ptshell.genericapi.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ptshell.genericapi.R;
import com.ptshell.genericapi.ui.fragment.FirstFragment;

public class MainActivity extends BaseActivity {
    private FirstFragment firstFragment;
    private final int FRAGMENT_KEY_FIRST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initActivity() {

    }

    @Override
    public void initView() {
        selectTab(FRAGMENT_KEY_FIRST);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListen() {

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (firstFragment == null && fragment instanceof FirstFragment)
            firstFragment = (FirstFragment) fragment;
    }

    private void selectTab(int tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (tag) {
            case FRAGMENT_KEY_FIRST:
                if (firstFragment == null) {
                    firstFragment = new FirstFragment();
                    transaction.add(R.id.fl_content, firstFragment);
                } else {
                    transaction.show(firstFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
}