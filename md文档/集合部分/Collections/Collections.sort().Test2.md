#### 1..第二种方法是根据`Collections.sort`重载方法来实现;

```java
@SuppressWarnings({"unchecked", "rawtypes"})
public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);  
    }
```

**2.准备测试实体**

2.1. `BookPo`

```java
@Data
@AllArgsConstructor
class BookPo {

    private String bookName;

    private Integer bookPrice;

}
```

**3.测试**

```java
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
```

**4.测试结果**

```xml
[BookPo(bookName=《哲学家都干了些什么》, bookPrice=25), BookPo(bookName=《都挺好》, bookPrice=25), BookPo(bookName=《霍乱时期的爱情》, bookPrice=55), BookPo(bookName=《看见》, bookPrice=85)]
```

