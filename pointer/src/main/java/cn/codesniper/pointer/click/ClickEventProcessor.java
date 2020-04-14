package cn.codesniper.pointer.click;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import cn.codesniper.pointer.process.EventParser;
import cn.codesniper.pointer.process.EventProducer;
import cn.codesniper.pointer.service.TraceService;
import cn.codesniper.pointer.service.TraceServiceImpl;
import cn.codesniper.pointer.unit.TracePoint;
import cn.codesniper.pointer.unit.PointerEventType;
import cn.codesniper.pointer.unit.TraceViewRoot;

import static cn.codesniper.pointer.constant.PointerConfig.FRAGMENT_TAG_KEY;
import static cn.codesniper.pointer.constant.PointerConfig._TAG;

/**
 * 通过点击拦截方式进行点击拦截
 */
public class ClickEventProcessor extends View.AccessibilityDelegate {

    private static final String TAG = "ClickEventProcessor";


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
        //找到我们的内容视图
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
        return view.getVisibility() == View.VISIBLE && view.isClickable() && ViewCompat.hasOnClickListeners(view);
    }

    @Override
    public void sendAccessibilityEvent(View host, int eventType) {
        super.sendAccessibilityEvent(host, eventType);
        if (AccessibilityEvent.TYPE_VIEW_CLICKED == eventType && host != null) {
            Log.v(TAG,"当前点击的View类型为:"+host.getClass().getSimpleName());
            //拦截到View后开始组装完整的埋点对象
            TracePoint point=EventProducer.producePoint(host, PointerEventType.APP_CLICK,"");
            String bizName=EventParser.getInstance().getTraceBiz(point.getCompleteViewPath());
            point.setBizName(bizName);
            if(!TextUtils.isEmpty(bizName)){
                TraceService traceService=new TraceServiceImpl();
                traceService.traceViewClick("TraceModule",TAG,bizName);
            }
//            Log.v(TAG,point.toString());
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