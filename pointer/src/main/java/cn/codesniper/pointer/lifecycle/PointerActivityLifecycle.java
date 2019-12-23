package cn.codesniper.pointer.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.codesniper.pointer.R;
import cn.codesniper.pointer.util.HookUtil;

public class PointerActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d("xxx","onActivityCreated");
        PointerAppManager.getAppManager().addActivity(activity);

        if(activity instanceof FragmentActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((FragmentActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new PointerFragmentLifecycle(),true);
            }
        }
    }

    private void hookViewGroup(ViewGroup viewGroup){
        Log.d("xxx","&&"+viewGroup.getChildCount()+"&&"+viewGroup.getClass().getSimpleName());
        for(int i=0;i<viewGroup.getChildCount();i++){
            View view=viewGroup.getChildAt(i);
            if(view instanceof  ViewGroup){
                if(view instanceof RecyclerView){
                    Log.d("xxx","rv");
                    RecyclerView rv= (RecyclerView) view;
                    HookUtil.hookView(rv.getRootView());
                }else {
                    hookViewGroup((ViewGroup) view);
                }
            }else if(view instanceof ViewStub){
                ViewStub viewStub= (ViewStub) view;
                hookViewGroup((ViewGroup) viewStub.inflate());
            }else if(view instanceof View) {
                HookUtil.hookView(view);
            }
        }
    }


    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        View view=activity.getWindow().getDecorView();
        hookViewGroup((ViewGroup) view);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        PointerAppManager.getAppManager().removeActivity(activity);
    }
}
