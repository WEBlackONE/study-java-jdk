package com.sibo.map.test;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * @Author:zhangYu Date: Created in 2019/4/28.
 * Description:
 */
public class WeakHashMapTest {

    public static void main(String[] args) {
        System.out.println(test());
    }

    // 求最终打印结果
    private static String test(){
        String a = new String("a");
        WeakReference<String> b = new WeakReference<String>(a);
        WeakHashMap<String, Integer> weakMap = new WeakHashMap<String, Integer>();
        weakMap.put(b.get(), 1);
        a = null;
        System.gc();
        String c = "";
        try{
            c = b.get().replace("a", "b");
            return c;
        }catch(Exception e){
            c = "c";
            return c;
        }finally{
            c += "d";
            return c + "e";
        }
    }
}
