package cn.codesniper.pointer;

import android.app.Application;


import cn.codesniper.pointer.lifecycle.PointerActivityLifecycle;
import cn.codesniper.pointer.unit.Point;

/**
 * 打点系统
 */
public class PointerManager {

    private Application application;

    private static volatile PointerManager singleton;

    private PointerManager(Application application) {
        this.application=application;
    }

    public void init(){
        application.registerActivityLifecycleCallbacks(new PointerActivityLifecycle());
    }



    public static PointerManager getInstance(Application application) {
        if (singleton == null) {
            synchronized (PointerManager.class) {
                if (singleton == null) {
                    singleton = new PointerManager(application);
                }
            }
        }
        return singleton;
    }


    /**
     * 打点时 根据view路径 解析事件类型 再根据数据路径取值 字符串 进行打点
     * @param point
     */
    public static void report(Point point){


    }




}
