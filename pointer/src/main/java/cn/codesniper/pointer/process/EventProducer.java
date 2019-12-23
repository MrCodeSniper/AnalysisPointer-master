package cn.codesniper.pointer.process;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.codesniper.pointer.lifecycle.PointerAppManager;
import cn.codesniper.pointer.unit.PointerEventType;
import cn.codesniper.pointer.unit.Point;
import cn.codesniper.pointer.unit.ViewRoot;


public class EventProducer {

     public static Point producePoint(View view, PointerEventType eventType,String dataRoute){
         Point point=new Point();
         point.setEventType(eventType);
         point.setViewPath(new ViewRoot(view,view.getContext().getClass().getSimpleName()));
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

         point.setPageName(activityName+"&"+fragmentName);
         point.setDataPath(dataRoute);
         return point;
     }


}
