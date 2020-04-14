package cn.codesniper.pointer.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static cn.codesniper.pointer.constant.PointerConfig._TAG;

public class ClassUtils {

    /**
     * 获取这个类的所有父类
     * @param clazz
     * @return
     */
    public static List<Class<?>> getSuperClass(Class<?> clazz){
        List<Class<?>> clazzs=new ArrayList<Class<?>>();
        Class<?> suCl=clazz.getSuperclass();
        while(suCl!=null){
            Log.d(_TAG,suCl.getName());
            clazzs.add(suCl);
            suCl=suCl.getSuperclass();
        }
        return clazzs;
    }
}
