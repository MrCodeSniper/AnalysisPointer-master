package cn.codesniper.analysispointer;

import android.app.Application;

import cn.codesniper.pointer.PointerManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PointerManager.getInstance(this).init();
    }
}
