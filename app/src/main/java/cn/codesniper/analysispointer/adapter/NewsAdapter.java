package cn.codesniper.analysispointer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

import cn.codesniper.analysispointer.R;
import cn.codesniper.analysispointer.bean.News;

import static cn.codesniper.pointer.constant.PointerConfig._TAG;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<News> mDataItems;  //数据集合

    private Context mContext;

    public NewsAdapter(List<News> newsList, Context mContext) {
        this.mDataItems = newsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.rlroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(_TAG,"点击了item"+i);
                Log.d(_TAG,"点击了item"+ v.getParent().getClass().getSimpleName());
                RecyclerView view= (RecyclerView) v.getParent();

                Field field = null;
                try {
                    field = view.getAdapter().getClass().getDeclaredField("mDataItems");
                    field.setAccessible(true);
                    Log.d(_TAG, field.getGenericType().toString());
                    List datas= (List) field.get(view.getAdapter());
                    field = datas.get(i).getClass().getDeclaredField("title");
                    field.setAccessible(true);
                    Log.d(_TAG,"点击了item"+ field.getGenericType().toString());

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        });
        News news = mDataItems.get(i);
        viewHolder.tvtitle.setText(news.getTitle());
        viewHolder.tvSouce.setText(news.getSource());
        viewHolder.tvTime.setText(news.getTime());
    }

    @Override
    public int getItemCount() {
        return mDataItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlroot;
        TextView tvtitle;
        TextView tvSouce;
        TextView tvTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.news_title);
            tvTime = itemView.findViewById(R.id.news_publishtime);
            tvSouce = itemView.findViewById(R.id.news_source);
            rlroot=itemView.findViewById(R.id.rootview);
        }

    }
}
