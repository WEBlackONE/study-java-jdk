package com.sibo.map.test;

import com.sibo.collection.test.UserPo;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:zhangYu Date: Created in 2019/4/19.
 * Description:
 */
public class HashMapTest {

    @Test
    public void TestHashMapSynchronized(){
        Map<String, UserPo> HashMap = new HashMap<String, UserPo>();
        HashMap.put("1",new UserPo("JayceKoba",23));
    }



}
