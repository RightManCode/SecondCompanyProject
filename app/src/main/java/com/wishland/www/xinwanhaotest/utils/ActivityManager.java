package com.wishland.www.xinwanhaotest.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * 类使用单例模式
 */
public class ActivityManager {

    private ActivityManager() {

    }

    private static ActivityManager am = new ActivityManager();

    public static ActivityManager getActivityManager() {
        return am;
    }

    //获得栈的管理器，泛型Acitivity
    private Stack<Activity> stack = new Stack<>();

    /**
     * 添加（插入）Actvity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            stack.add(activity);
        }
    }

    /**
     * 删除指定的Activity
     * @param activity
     */
    public void deleteActivity(Activity activity) {
        for (int x = stack.size() - 1; x >= 0; x--) {
            //对比栈中的实例的类 和 activity的类 是否一样
            if (stack.get(x).getClass().equals(activity.getClass())) {
                activity.finish();
                stack.remove(x);
            }
        }
    }

    public Context getActivity(Activity activity){
        for (int x = stack.size() - 1; x >= 0; x--) {
            //对比栈中的实例的类 和 activity的类 是否一样
            if (stack.get(x).getClass().equals(activity.getClass())) {
                activity.finish();
               return activity;
            }
        }
        return null;
    }

    /**
     * 删除当前的Activity (EX: 当前代表在栈顶，就时必须显示在屏幕界面)
     */
    public void deleteThisActivity() {
        Activity activity = stack.lastElement();
        activity.finish();
        stack.remove(activity);
    }

    /**
     * 删除所有的Activi
     */
    public void deleteAllActivity() {
        for (int x = stack.size() - 1; x >= 0; x--) {
            Activity activity = stack.get(x);
            activity.finish();
            stack.remove(x);
        }
    }

    /**
     * @return  返回栈的大小
     */
    public int getStackCount() {
        return stack.size();
    }
}
