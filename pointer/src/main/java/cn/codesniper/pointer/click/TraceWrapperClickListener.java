package cn.codesniper.pointer.click;

import android.util.Log;
import android.view.View;

/**
 * 埋点
 */
public class TraceWrapperClickListener implements View.OnClickListener {

    private static final String TAG = "TraceWrapperClickListen";

    private View.OnClickListener mDelegeter;

    public TraceWrapperClickListener(View.OnClickListener mDelegeter) {
        this.mDelegeter = mDelegeter;
        Log.d(TAG,"初始化"+mDelegeter);
    }

    @Override
    public void onClick(View v) {
        if(mDelegeter!=null) {
            mDelegeter.onClick(v);
            doPoint(v);
        }
    }

    private void  doPoint(View view){
        Log.d(TAG,"doPoint:"+view.getId());
        //TODO 1.收集view的类信息 基础信息 以及数据信息  2.生成ViewPath 3.读取JSON配置对应的策略和TAG 4.打下TAG
    }

}
