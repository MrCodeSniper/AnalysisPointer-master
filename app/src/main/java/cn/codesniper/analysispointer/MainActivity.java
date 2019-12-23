package cn.codesniper.analysispointer;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.codesniper.pointer.delegate.ClickEventProcessor;
import cn.codesniper.pointer.process.DataPathParser;
import cn.codesniper.pointer.util.HookUtil;

public class MainActivity extends Activity {

    private String mText="";
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));  //线性布局
        //3.准备数据
        List<News> newsList=new ArrayList<>();

        News news;
        for(int i=1;i<=99;i++){
            news=new News();
            news.setTitle("以习近平同志为核心的党中央坚定不移推进全面深化改革"+i);
            news.setSource("新华网"+i);
            news.setTime("2018-8-6");
            newsList.add(news);
        }

        //3.设置适配器
        NewsAdapter newsAdapter=new NewsAdapter(newsList,this);
        rv.setAdapter(newsAdapter);
        final TextView tv=findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("xxx",tv.getId()+"");
                String path="this.context.mText";
                DataPathParser dataPathParser=new DataPathParser();
                dataPathParser.parse(tv,path);

            }
        });




        //ContentView/MainActivity[0]/FrameLayout[0]/ActionBarOverlayLayout[0]/ContentFrameLayout[0]/ConstraintLayout[0]/AppCompatTextView[0]
        //ContentFrameLayout[0]内容父布局
        ClickEventProcessor.getInstance().setActivityTracker(this);


    }





}
