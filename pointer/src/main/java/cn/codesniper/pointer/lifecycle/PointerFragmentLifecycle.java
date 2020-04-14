package cn.codesniper.pointer.lifecycle;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import cn.codesniper.pointer.click.ClickEventProcessor;
import cn.codesniper.pointer.click.ClickHookProcessor;
import cn.codesniper.pointer.click.TraceWrapperClickListener;

/**
 * 页面埋点 Type:Fragment
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class PointerFragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    public PointerFragmentLifecycle() {
        super();
    }

    @Override
    public void onFragmentPreAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentPreAttached(fm, f, context);
    }

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
    }

    @Override
    public void onFragmentPreCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentPreCreated(fm, f, savedInstanceState);
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        PointerAppManager.getAppManager().addFragment(f);
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
        ClickEventProcessor.getInstance().setFragmentTracker(f);
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        PointerAppManager.getAppManager().removeFragment(f);
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
    }
}
