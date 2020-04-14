package cn.codesniper.pointer.service;

import android.util.Log;

public class TraceServiceImpl implements TraceService {

    private static final String TAG = "TraceServiceImpl";

    @Override
    public void traceViewClick(String moduleName, String tag, String traceTag) {
        Log.e(TAG,String.format("埋点成功！模块名:%s,日志TAG:%s,埋点业务名:%s",moduleName,tag,traceTag));
    }
}
