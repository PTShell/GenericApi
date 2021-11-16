package com.ptshell.genericapi.ui.activity;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initActivity();
        initView();
        initData();
        initListen();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 设置布局
     */
    public abstract void setContentView();

    /**
     * 初始化Activity
     */
    public abstract void initActivity();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听
     */
    public abstract void initListen();

}