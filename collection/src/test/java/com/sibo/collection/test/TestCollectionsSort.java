package com.sibo.collection.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;

/**
 * @Author:zhangYu Date: Created in 2019/4/11.
 * Description:
 */
public class TestCollectionsSort {


    UserPo userPo1 = new UserPo("苏明玉",25);
    UserPo userPo2 = new UserPo("苏明成",35);
    UserPo userPo3 = new UserPo("苏明哲",45);
    UserPo userPo4 = new UserPo("苏大强",55);




    @Test
    public void testCollectionsSortimplementsComparable(){
        //test: situation 1
        List<UserPo> userPos = new ArrayList<UserPo>();

        userPos.add(userPo2);
        userPos.add(userPo4);
        userPos.add(userPo1);
        userPos.add(userPo3);
        Collections.sort(userPos);
        System.out.println(userPos);
    }
    @Test
    public void testCollectionsSort(){

        BookPo bookPo1 = new BookPo("《霍乱时期的爱情》", 55);
        BookPo bookPo2 = new BookPo("《看见》", 85);
        BookPo bookPo3 = new BookPo("《哲学家都干了些什么》", 25);
        BookPo bookPo4 = new BookPo("《都挺好》", 25);
        //test: situation 2
        List<BookPo> bookPos = new ArrayList<BookPo>();
        bookPos.add(bookPo1);
        bookPos.add(bookPo2);
        bookPos.add(bookPo3);
        bookPos.add(bookPo4);

        Collections.sort(bookPos,new Comparator<BookPo>() {
            public int compare(BookPo o1, BookPo o2) {
                return o1.getBookPrice().compareTo(o2.getBookPrice());
            }
    });
        System.out.println(bookPos);
    }




}
