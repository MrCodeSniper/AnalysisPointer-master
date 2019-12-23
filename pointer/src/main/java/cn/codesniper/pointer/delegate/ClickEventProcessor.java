package cn.codesniper.pointer.delegate;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import cn.codesniper.pointer.process.EventProducer;
import cn.codesniper.pointer.unit.Point;
import cn.codesniper.pointer.unit.PointerEventType;
import cn.codesniper.pointer.unit.ViewRoot;

import static cn.codesniper.pointer.PointerConfig.FRAGMENT_TAG_KEY;
import static cn.codesniper.pointer.PointerConfig._TAG;


public class ClickEventProcessor extends View.AccessibilityDelegate {




    private ClickEventProcessor() {}

    private static class Singleton {
        private final static ClickEventProcessor instance = new ClickEventProcessor();
    }

    public static ClickEventProcessor getInstance() {
        return Singleton.instance;
    }

    /**
     * 设置Activity页面中View的事件监听
     * @param activity
     */
    public void setActivityTracker(Activity activity) {
        //找到contentview
        View contentView = activity.findViewById(android.R.id.content);
        if (contentView != null) {
            setViewClickedTracker(contentView, null);
        }
    }

    /**
     * 设置Fragment页面中View的事件监听
     * @param fragment
     */
    public void setFragmentTracker(Fragment fragment) {
        View contentView = fragment.getView();
        if (contentView != null) {
            setViewClickedTracker(contentView, fragment);
        }
    }

    private void setViewClickedTracker(View view, Fragment fragment) {
        if (view == null) {
            return;
        }

        if (needTracker(view)) {
            if (fragment != null) {
                view.setTag(FRAGMENT_TAG_KEY, fragment);
            }
            view.setAccessibilityDelegate(this);
        }
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewClickedTracker(((ViewGroup) view).getChildAt(i), fragment);
            }
        }
    }

    private boolean needTracker(View view) {
        if (view.getVisibility() == View.VISIBLE && view.isClickable() && ViewCompat.hasOnClickListeners(view)) {
            return true;
        }
        return false;
    }

    @Override
    public void sendAccessibilityEvent(View host, int eventType) {
        super.sendAccessibilityEvent(host, eventType);
        if (AccessibilityEvent.TYPE_VIEW_CLICKED == eventType && host != null) {
            Log.e("xxx",host.getClass().getSimpleName());
            Log.e("xxx",new ViewRoot(host,host.getContext().getClass().getSimpleName()).getCompletePath());
            //拦截到View后开始组装Point对象
            Point point=EventProducer.producePoint(host, PointerEventType.APP_CLICK,"");
            Log.d(_TAG,point.toString());
          //  Tracker.getInstance().addClickEvent(host, (Fragment) host.getTag(FRAGMENT_TAG_KEY));
          //  sensor(host);
        }
    }




    private void sensor(View view){
//        try {
//            Field field= view.getContext().getClass().getDeclaredField("newGoodsListEntity");
//            field.setAccessible(true);
//            NewGoodsListEntity banners = (NewGoodsListEntity) field.get(view.getContext());
//            Log.e("xxx",banners.getGoods().size()+"&&"+banners.getGoods().get(0).getText()+"&&"+banners.getGoods().get(0).getPrice());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }


}