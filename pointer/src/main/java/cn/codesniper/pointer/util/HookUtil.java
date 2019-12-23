package cn.codesniper.pointer.util;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.codesniper.pointer.delegate.WrapperClickListener;

public class HookUtil {

    public static void hookView(View view) {
        Log.d("xxx",view.getClass().getSimpleName()+"");
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
            View.OnClickListener onClickListenerProxy = new WrapperClickListener(mOnClickListener);
            //更换
            onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
        } catch (Exception e) {
            Log.d("xxx",e.getMessage());
            e.printStackTrace();
        }
    }











}




