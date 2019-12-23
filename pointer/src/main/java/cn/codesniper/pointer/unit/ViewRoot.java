package cn.codesniper.pointer.unit;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

public class ViewRoot {

    private String viewType;
    private String itemPath;
    private int index;
    private View mView;
    private String completePath;
    private String clz;

    public String getItemPath() {
        return itemPath;
    }

    public ViewRoot(View view, String clz) {
        this.clz=clz;
        this.mView=view;
        this.viewType =view.getClass().getSimpleName();
        this.index=getIndexFromParent(view);
        this.itemPath=this.viewType+"["+index+"]";
        completePath=this.itemPath;
        initViewPath(view.getParent());
    }

    public String getCompletePath() {
        return completePath;
    }

    private void initViewPath(ViewParent view) {
        do{
            String layoutName=view.getClass().getSimpleName();
            if(view.getParent().getClass().getSimpleName().equals("DecorView")){
                layoutName=clz;
                completePath= layoutName+"["+getIndexFromParent(view)+"]"+"/"+completePath;
            }else if(view.getClass().getSimpleName().equals("DecorView")){
                completePath= "ContentView"+"/"+completePath;
            }else {
                completePath= layoutName+"["+getIndexFromParent(view)+"]"+"/"+completePath;
            }

            view=view.getParent();
        }while (view.getParent()!=null);
    }


    public  int getIndexFromParent(View view){
            if(view!=null && view.getParent()!=null ){
                ViewGroup viewParent= (ViewGroup) view.getParent();
                List<View> list=new ArrayList<>();
                for(int i=0;i<viewParent.getChildCount();i++){
                    if(viewParent.getChildAt(i).getClass()==view.getClass()){
                        list.add(viewParent.getChildAt(i));
                    }
                }

                for(int i=0;i<list.size();i++){
                    if(list.get(i)==view){
                        return i;
                    }
                }
            }
        return 0;
    }


    /**
     * 获取view在父布局的的子view中的index
     * @param view
     * @return
     */
    public  int getIndexFromParent(ViewParent view){

        if(!checkPrediction(view)) return 0;

        if(view.getParent()!=null){
            ViewGroup viewParent= (ViewGroup) view.getParent();
            List<View> list=new ArrayList<>();
            for(int i=0;i<viewParent.getChildCount();i++){
                if(viewParent.getChildAt(i).getClass()==view.getClass()){
                    list.add(viewParent.getChildAt(i));
                }
            }

            for(int i=0;i<list.size();i++){
                if(list.get(i)==view){
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * 是否为根节点
     * @param view
     * @return
     */
    private boolean checkPrediction(ViewParent view){
       if(TextUtils.equals(view.getParent().getClass().getSimpleName(),"ViewRootImpl")){
           return false;
        }
        return true;
    }

}
