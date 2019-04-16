package com.sibo.collection.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Author:zhangYu Date: Created in 2019/4/11.
 * Description:
 */
@Data
@AllArgsConstructor
@ToString
public class UserPo implements Comparable<UserPo>{

    private String name;
    //要进行封箱处理
    private Integer age;



    public int compareTo(UserPo o) {
        return this.age.compareTo(o.age);
    }
}
