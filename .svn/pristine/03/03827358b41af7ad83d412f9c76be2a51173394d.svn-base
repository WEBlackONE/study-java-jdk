package com.sibo.collection.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author:zhangYu Date: Created in 2019/4/15.
 * Description:
 */
public class TestCollectionsMinMax {

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

//        Collections.sort(bookPos,new Comparator<BookPo>() {
//            public int compare(BookPo o1, BookPo o2) {
//                return o1.getBookPrice().compareTo(o2.getBookPrice());
//            }
//        });
        BookPo min = Collections.max(bookPos,new Comparator<BookPo>() {
            public int compare(BookPo o1, BookPo o2) {
                return o1.getBookPrice().compareTo(o2.getBookPrice());
            }
        });
        System.out.println(min);
    }
}
