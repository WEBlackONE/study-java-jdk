package com.sibo.set.test;

import com.sibo.collection.test.UserPo;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author:zhangYu Date: Created in 2019/4/18.
 * Description:
 */
public class HashSetLinkedHashSetTest {

    @Test
    public void HashSetLinkedHashSetTest(){

        UserPo userPo1 = new UserPo("苏明玉",25);
        UserPo userPo2 = new UserPo("苏明成",35);
        UserPo userPo3 = new UserPo("苏明哲",45);
        UserPo userPo4 = new UserPo("苏大强",55);
        Set<UserPo> hashSet = new HashSet<UserPo>();
        Set<UserPo> linkedHashSet = new LinkedHashSet<UserPo>();

//        boolean add1 = hashSet.add(userPo1);
//        System.out.println(add1);
//
//        boolean add2 = hashSet.add(userPo2);
//        System.out.println(add2);
//        boolean add3 = hashSet.add(userPo3);
//        System.out.println(add3);
//        boolean add4 = hashSet.add(userPo4);
//        System.out.println(add4);
//        boolean add5 = hashSet.add(userPo1);
//        System.out.println(add5);
//
//        System.out.println(hashSet);

        boolean add1 = linkedHashSet.add(userPo1);
        System.out.println(add1);

        boolean add2 = linkedHashSet.add(userPo2);
        System.out.println(add2);
        boolean add3 = linkedHashSet.add(userPo3);
        System.out.println(add3);
        boolean add4 = linkedHashSet.add(userPo4);
        System.out.println(add4);
        boolean add5 = linkedHashSet.add(userPo1);
        System.out.println(add5);

        System.out.println(linkedHashSet);

    }
}
