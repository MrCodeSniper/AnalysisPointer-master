package cn.codesniper.pointer.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import cn.codesniper.pointer.click.ClickEventProcessor;
import cn.codesniper.pointer.click.ClickHookProcessor;

/**
 * 页面埋点 Type:Activity
 */
public class PointerActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "PointerActivityLifecycl";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG,"onActivityCreated");
        PointerAppManager.getAppManager().addActivity(activity);

        if(activity instanceof FragmentActivity){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((FragmentActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new PointerFragmentLifecycle(),true);
            }
        }
    }

    /**
     * 拿到Activity的DecorView视图树 递归树
     * @param viewGroup
     */
    private void hookViewGroup(ViewGroup viewGroup){
        Log.d(TAG,"当前ViewGroup::"+viewGroup.getClass().getSimpleName()+"的子View数量:"+viewGroup.getChildCount());
        for(int i=0;i<viewGroup.getChildCount();i++){
            View view=viewGroup.getChildAt(i);
            if(view instanceof  ViewGroup){
                if(view instanceof RecyclerView){
                    Log.d(TAG,"Hook RecyclerView");
                    RecyclerView rv= (RecyclerView) view;
                    ClickHookProcessor.hookView(rv.getRootView());
                }else {
                    hookViewGroup((ViewGroup) view);
                }
            }else if(view instanceof ViewStub){ //兼容ViewStub懒加载情况
                ViewStub viewStub= (ViewStub) view;
                hookViewGroup((ViewGroup) viewStub.inflate());
            }else if(view != null) { //如果是叶子view
                ClickHookProcessor.hookView(view);
            }
        }
    }


    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ClickEventProcessor.getInstance().setActivityTracker(activity);
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
