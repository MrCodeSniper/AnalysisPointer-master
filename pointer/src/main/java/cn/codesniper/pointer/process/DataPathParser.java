package cn.codesniper.pointer.process;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.codesniper.pointer.lifecycle.PointerAppManager;
import cn.codesniper.pointer.util.ClassUtils;

import static cn.codesniper.pointer.PointerConfig._TAG;

//https://www.jianshu.com/p/b5ffe845fe2d

/**
 * 我们打点时只需要字符串即可
 */
public class DataPathParser {

    //表示对象所属关系，如：ａ.b 表示实例a中的字段b
    public static final String SEPARATOR_BELONG = ".";

    //表示公有方法调用，如：ａ.b() 表示调用实例a中的方法b.注意：方法入参可以是DataPath指向的Object
    public static final String SEPARATOR_INVOKE_METHOD = "()";

    //数组/线性表的index. 注意：此index可以是常量数字，也可以是一个DataPath指向的数字
    public static final String SEPARATOR_CONTAINER_INDEX = "[]";

    //DataPath字符串的起点，表示起点为当前实例（当前View）
    public static final String SEPARATOR_THIS = "this";

    //DataPath字符串的起点，表示起点为当前View父节点中AdapterView adapter中当前条目. 常用于列表中的数据获取
    public static final String SEPARATOR_ITEM = "item";

    //DataPath节点中的关键字，用于表示当前view的parentView.效果同view.getParent(),使用此关键字可减少视图引用中的反射
    public static final String SEPARATOR_PARENT = "parent";

    //DataPath节点中的关键字，用于表示当前view的第x个childView.效果同view.getChildAt(x),使用此关键字可减少视图引用中的反射
    public static final String SEPARATOR_CHILD = "childAt";

    public static final String OBJ_CONTEXT = "context";


    public void parse(View view, String path) {
        //1.先判断开头部分起点 是item还是this
        if (TextUtils.isEmpty(path)) return;

        try {
            if (path.startsWith(SEPARATOR_THIS)) {
                if (path.startsWith(SEPARATOR_THIS + SEPARATOR_BELONG + OBJ_CONTEXT)) {
                    processContextData(view.getContext(), path);
                } else if (path.startsWith(SEPARATOR_THIS + SEPARATOR_BELONG + SEPARATOR_PARENT)) {
//                    processParentView();
                } else if (path.startsWith(SEPARATOR_THIS + SEPARATOR_BELONG + SEPARATOR_CHILD)) {
//                    processChildView();
                } else {
                    processViewData(view, path);
                }
            } else if (path.startsWith(SEPARATOR_ITEM)) {
                processItemView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //起点定为＂item＂,则表示从此ListView(或者RecylerView)绑定的Adapter中当前数据item为起点取数据．
    //item.productId
    private void processItemView() {

    }


    private void processParentView(View view, String path) throws Exception {
        processViewData((View) view.getParent(), path);
    }


    private void processChildView(ViewGroup view, String path, int childIndex) throws Exception {
        processViewData(view.getChildAt(childIndex), path);
    }

    //处理view相关数据
    private void processViewData(View view, String path) throws Exception {
        /**
         * 列出可能的view情况
         * this.id this.getId() this.datas[5]
         */
        String[] pathFilter = path.split("\\" + SEPARATOR_BELONG);

        String viewScope = pathFilter[0];

        String viewNext = pathFilter[1];

        if (!TextUtils.isEmpty(viewNext)) {

            if (viewNext.contains(SEPARATOR_INVOKE_METHOD)) {
                //认为是调用函数 拿到返回值 this.getId() 返回值类型
                String methodName = viewNext.replace(SEPARATOR_INVOKE_METHOD, "");
                Log.d(_TAG, "方法名:" + methodName);
                Method method = view.getClass().getDeclaredMethod(methodName, null);
                method.setAccessible(true);
                Object returnV = method.invoke(view);
                Log.d(_TAG, returnV.toString());
            } else if (viewNext.contains("[") && viewNext.contains("]")) {
                String pattern = "\\d+";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(viewNext);
                if (m.find()) {
                    //认为是取线性表数据 this.list[5]
                    int index = Integer.parseInt(m.group());

                    String fieldName = viewNext.replace("[", "").replace("]", "").replace(m.group(), "");
                    Field field = view.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);

                    Log.d(_TAG, field.getGenericType().toString());
                    if (field.getGenericType().toString().equals("class [Ljava.lang.String;")) {
                        //数组类型
                        String[] resultStrs = (String[]) field.get(view);
                        Log.d(_TAG, resultStrs[index]);
                    } else if (field.getGenericType().toString().equals("java.util.List<java.lang.String>")) {
                        //列表类型 字符串
                        List<String> resultList = (List<String>) field.get(view);
                        Log.d(_TAG, resultList.get(index));
                    }

                }

            } else {
                //认为是取全局变量 this.mText
                String fieldName = viewNext;
                Log.d(_TAG, "全局变量名:" + fieldName);
                Field field = view.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object object = field.get(view);
                Log.d(_TAG, "成员变量值:" + object.toString());
            }
        }
    }


    private boolean isKeyWord(String field) {

        return false;

    }


    //处理 Activity Fragment相关数据
    //例如 this.context.mText 字段
    //this.context.datas[5] 数组列表
    //this.context.getdata()
    private void processContextData(Context context, String path) throws Exception {
        String fieldName = path.replace(SEPARATOR_BELONG, "").replace(SEPARATOR_THIS, "").replace(OBJ_CONTEXT, "");
        Log.d(_TAG, "fieldName:" + fieldName);
        Activity activity = PointerAppManager.getAppManager().getActivity(context.getClass());
        Field field = null;

        field = activity.getClass().getDeclaredField(fieldName);
        Log.d(_TAG, "Name:" + context.getClass().getSimpleName() + "type:" + field.getGenericType().toString());
        field.setAccessible(true);

        if (field.getGenericType().toString().equals("class java.lang.String")) {
            String fieldValue = (String) field.get(activity);
            Log.d(_TAG, "Name:" + context.getClass().getSimpleName() + "Value:" + fieldValue);
        }


    }


}
