package com.sh;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@SpringBootTest
public class CollectionTest {

    private static Logger logger = LoggerFactory.getLogger(CollectionTest.class);

    @Test
    public void sort() {
        List<Map<String,Object>> list = getList();
        logger.info("【list<map>排序】 排序前：{}", JSONArray.toJSONString(list));
        list.sort((Map<String,Object> m1,Map<String,Object> m2) ->
                ((String) m1.get("code")).compareTo((String) m2.get("code")));
        logger.info("【list<map>排序】 排序后：{}", JSONArray.toJSONString(list));
    }


    @Test
    public void filter() {
        List<Map<String,Object>> list = getList();
        logger.info("【list<map>筛选】 筛选：{}", JSONArray.toJSONString(list));
        list = list.stream().filter(map -> ((String) map.get("name")).startsWith("c"))
                .collect(toList());
        logger.info("【list<map>筛选】 筛选后：{}", JSONArray.toJSONString(list));
    }

    /**
     * java.util 包的集合类就都是快速失败
     * 迭代器在遍历时直接访问集合中的内容，增加或删除元素时都会修改modCount变量，
     * 并抛出ConcurrentModificationException 异常
     */
    @Test
    public void failFast() {
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "11");
        map.put("2", "22");
        map.put("3", "33");
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            map.put("2", "222");    // 不抛异常
            map.put("5", "55");     // 抛异常
        }
    }


    /**
     * java.util.concurrent 包下的类都是安全失败
     * 采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，
     * 而是先复制原有集合内容，在拷贝的集合上进行遍历。
     */
    @Test
    public void failSafe() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "11");
        map.put("2", "22");
        map.put("3", "33");
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            map.put("2", "222");    // 不抛异常
            map.put("5", "55");     // 不抛异常
        }
    }



    private List<Map<String,Object>> getList() {

        List<Map<String,Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("name", "beijing");
        map1.put("code", "5");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", "shanghai");
        map2.put("code", "1");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("name", "chongqing");
        map3.put("code", "6");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("name", "tianjin");
        map4.put("code", "0");
        list.add(map4);

        return list;

    }


    /**
     *  Arrays.asList(arr); 方法返回Arrays类的内部类ArrayList
     *  同时通过构造函数将arr的引用给内部类ArrayList的类变量
     *  内部类ArrayList重写了get set size等方法直接对arr进行操作
     *  但add remove方法没有重写 直接调用使用父类AbstractList的方法
     *  @author micomo
     *  @date 2020/4/26 18:09
     *  @link https://blog.csdn.net/u014634309/article/details/105700463
     */
    @Test
    public void arrayToList() {
        String[] arr = {"a", "b", "c", "d"};
        List<String> list = Arrays.asList(arr);

        /**
         * array=[a, b, c, d]
         * list=[a, b, c, d]
         */
        logger.info("array={}", Arrays.toString(arr));
        logger.info("list={}", list);

        arr[0] = "x";
        list.set(1, "y");

        /**
         * array=[x, y, c, d]
         * list=[x, y, c, d]
         */
        logger.info("array={}", Arrays.toString(arr));
        logger.info("list={}", list);


        // throw java.lang.UnsupportedOperationException
        list.add("z");
        // throw java.lang.UnsupportedOperationException
        list.remove(3);

        /**
         * 推荐使用
         * newArrayList(arr) 方法会新建一个ArrayList对象list
         * 同时把数组元素添加到list中
         */
        List<String> strList = Lists.newArrayList(arr);
        strList.add("e");
        logger.info("strList={}", strList);
    }

}
