package com.ptshell.genericapi;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private ArrayList<Activity> activityList = new ArrayList<>();

    public static BaseApplication getInstance() {
        if (instance == null) {
            return new BaseApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 添加到ArrayList<Activity>
     *
     * @param activity：Activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 退出所有的Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}