package com.sibo.list.test;

import com.sibo.collection.test.UserPo;
import org.junit.Test;

import java.util.List;

/**
 * @Author:zhangYu Date: Created in 2019/4/16.
 * Description:
 */
public class ArrayList {

    public static void main(String[] args) {
        int a = 2;
        System.out.println(a<<1);
    }
    @Test
    public void removeAllTest(){
        List<UserPo> userPos1 = new java.util.ArrayList<UserPo>();
        UserPo userPo1 = new UserPo("苏明玉",25);
        UserPo userPo2 = new UserPo("苏明成",35);
        UserPo userPo3 = new UserPo("苏明哲",45);
        UserPo userPo4 = new UserPo("苏大强",55);
        userPos1.add(userPo2);
        userPos1.add(userPo4);
        userPos1.add(userPo1);
        userPos1.add(userPo3);
        ((java.util.ArrayList<UserPo>) userPos1).trimToSize();
        userPos1.add(userPo1);
        userPos1.add(userPo2);
        userPos1.add(userPo3);


        System.out.println(userPos1);
//        List<UserPo> userPos2 = new java.util.ArrayList<UserPo>();
//        userPos2.add(userPo4);
//        userPos1.removeAll(userPos2);
//        System.out.println(userPos1);

    }
}
