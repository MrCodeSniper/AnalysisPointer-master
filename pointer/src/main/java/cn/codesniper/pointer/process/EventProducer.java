package cn.codesniper.pointer.process;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import cn.codesniper.pointer.lifecycle.PointerAppManager;
import cn.codesniper.pointer.unit.PointerEventType;
import cn.codesniper.pointer.unit.TracePoint;
import cn.codesniper.pointer.unit.TraceViewRoot;

/**
 * 快捷生成 埋点事件
 */
public class EventProducer {

     public static TracePoint producePoint(View view, PointerEventType eventType, String dataRoute){
         TracePoint point=new TracePoint();
         //设置事件类型
         point.setEventType(eventType);
         //设置view路径
         point.setViewPath(new TraceViewRoot(view,view.getContext().getClass().getSimpleName()));
         //可以通过Applicaiton拿到当前page
         Activity activity=PointerAppManager.getAppManager().currentActivity();
         String activityName="";
         if(activity!=null){
              activityName=PointerAppManager.getAppManager().currentActivity().getLocalClassName();
         }

         Fragment fragment=PointerAppManager.getAppManager().currentFragment();
         String fragmentName="";
         if(fragment!=null){
             fragmentName=fragment.getClass().getName();
         }
         //设置页面名
         if(TextUtils.isEmpty(activityName)&&TextUtils.isEmpty(fragmentName)){
             point.setPageName("");
         }else if(!TextUtils.isEmpty(activityName)&&TextUtils.isEmpty(fragmentName)){
             point.setPageName(activityName);
         }else if(TextUtils.isEmpty(activityName)&&!TextUtils.isEmpty(fragmentName)){
             point.setPageName(fragmentName);
         }else{
             point.setPageName(activityName+"&"+fragmentName);
         }
         //设置数据路径
         point.setDataPath(dataRoute);
         return point;
     }


}
