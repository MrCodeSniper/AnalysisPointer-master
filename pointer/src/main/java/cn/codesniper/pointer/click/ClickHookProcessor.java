package cn.codesniper.pointer.click;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 拿到View点击事件监听器 包装一层 用来做埋点
 * 缺点 通过反射实现 1.性能问题 2.如果源码修改 这边也需要修改 可能涉及到OEM适配
 */
public class ClickHookProcessor {

    private static final String TAG = "ViewHookUtil";

    public static void hookView(View view) {
        Log.d(TAG,"当前Hook子view:"+view.getClass().getSimpleName());
        try {
            Class viewClazz = Class.forName("android.view.View");
            //事件监听器都是这个实例保存的
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");

            listenerInfoMethod.setAccessible(true);

            Object listenerInfoObj = listenerInfoMethod.invoke(view);

            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");

            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");

            onClickListenerField.setAccessible(true);

            View.OnClickListener mOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
            //自定义代理事件监听器
            View.OnClickListener onClickListenerProxy = new TraceWrapperClickListener(mOnClickListener);
            //更换
            onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
        } catch (Exception e) {
            //TODO 当找不到反射的类 可以降级拦截ClickEventProcessor
            Log.d(TAG,e.getMessage());
            e.printStackTrace();
        }
    }











}




