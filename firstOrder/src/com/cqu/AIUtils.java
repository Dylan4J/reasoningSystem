package com.cqu;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AIUtils {
    /**
     * 工具方法：根据身高体重获取bmi指数
     * @param weight
     * @param height
     * @return
     */
    public static double getBMI(double weight,double height){
        return weight / Math.pow(height / 100.0, 2);
    }

    /**
     * 获取一个对象的全部属性
     * @param
     */
    public Field[] getFields(Object o){
        Field[] field = o.getClass().getDeclaredFields();
        return field;
    }



}
