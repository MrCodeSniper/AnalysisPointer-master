package cn.codesniper.analysispointer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.codesniper.analysispointer.R;

public class ClickTraceActivity extends Activity {

    private static final String TAG = "ClickTraceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_trace_layout);
        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClickTraceActivity.this,MainActivity.class));
            }
        });
    }

    public void xml(View v){
        Toast.makeText(this,"Click XML",Toast.LENGTH_SHORT).show();
    }
}
