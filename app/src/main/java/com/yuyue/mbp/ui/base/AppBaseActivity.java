package com.yuyue.mbp.ui.base;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yuyue.mbp.R;
import com.yuyue.mbp.global.GlobalContext;

import java.util.List;
/**
 * Created by Renyx on 2018/6/7
 */
public abstract class AppBaseActivity extends Activity {
    private Toast mToast = null;
    private boolean background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (background) {
            background = false;
            onBackToForeground();
        }
    }

    protected void onBackToForeground() {}

    @Override
    protected void onResume() {
        super.onResume();
        GlobalContext.getInstance().setActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (GlobalContext.getInstance().getActivity() == this) {
            GlobalContext.getInstance().setActivity(null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isApplicationSentToBackground()) {
            background = true;
            onSentToBackground();
        }
    }

    /**
     * 切换到后台运行
     */
    protected void onSentToBackground() {}

    protected void setActionBarLayout(int layoutId) {
        ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(layoutId, null);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v, layout);
            initActionBarView(v);
        }
    }

    protected void initActionBarView(View v) {}

    protected void initView() {}

    /**
     * 显示Toast形式的提示信息
     * @param message
     */
    protected void showMessage(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 显示Toast形式提示信息
     * @param resId
     */
    protected void showMessage(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * 显示Toast形式的提示信息
     * @param message
     */
    protected void showMessage(String message, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 显示Toast形式提示信息
     * @param resId
     */
    protected void showMessage(int resId, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), resId, duration);
        } else {
            mToast.setText(resId);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    /**
     * 判断是否为切换到后台运行
     * @return
     */
    private boolean isApplicationSentToBackground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
