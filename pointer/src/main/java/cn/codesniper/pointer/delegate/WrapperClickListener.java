package cn.codesniper.pointer.delegate;

import android.util.Log;
import android.view.View;

public class WrapperClickListener implements View.OnClickListener {


    private View.OnClickListener mDelegeter;

    public WrapperClickListener(View.OnClickListener mDelegeter) {
        this.mDelegeter = mDelegeter;
        Log.d("xxx","初始化"+mDelegeter);
    }

    @Override
    public void onClick(View v) {
        Log.d("xxx","onClick");
        if(mDelegeter!=null) {
            mDelegeter.onClick(v);
            doPoint();
        }
    }

    private void  doPoint(){
        Log.d("POINTER","doPoint");
    }

}
