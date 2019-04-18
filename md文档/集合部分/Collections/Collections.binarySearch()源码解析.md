### 1.测试方法

```java
 @Test
    public void testCollectionsBinarySearch(){

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
        bookPos.add(bookPo1);

        // 先排个序
        Collections.sort(bookPos,new Comparator<BookPo>() {
            public int compare(BookPo o1, BookPo o2) {
                return o1.getBookPrice().compareTo(o2.getBookPrice());
            }
        });
		//测试一波
        int i = Collections.binarySearch(bookPos, bookPo1, new Comparator<BookPo>() {
            public int compare(BookPo o1, BookPo o2) {
                return o1.getBookPrice().compareTo(o2.getBookPrice());
            }
        });
        System.out.println(i);
    }
```

### 2.源码

2.1.索引折半查找

```java
private static <T> int indexedBinarySearch(List<? extends T> l, T key, Comparator<? super T> c) {
        int low = 0;
        int high = l.size()-1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = l.get(mid);//获取折半值
            int cmp = c.compare(midVal, key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }
```

2.2.构造器折半查找

```java
private static <T> int iteratorBinarySearch(List<? extends T> l, T key, Comparator<? super T> c) {
    int low = 0;
    int high = l.size()-1;
    // 获取折半值
    ListIterator<? extends T> i = l.listIterator();

    while (low <= high) {
        int mid = (low + high) >>> 1;
        T midVal = get(i, mid);
        int cmp = c.compare(midVal, key);

        if (cmp < 0)
            low = mid + 1;
        else if (cmp > 0)
            high = mid - 1;
        else
            return mid; // key found
    }
    return -(low + 1);  // key not found
}
```

