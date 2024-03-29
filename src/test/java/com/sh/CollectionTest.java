package com.sh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.sh.entity.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@SpringBootTest
public class CollectionTest {

    private static Logger logger = LoggerFactory.getLogger(CollectionTest.class);

    @Test
    public void sort() {
        List<Map<String, Object>> list = getList();
        logger.info("【list<map>排序】 排序前：{}", JSONArray.toJSONString(list));
        list.sort((Map<String, Object> m1, Map<String, Object> m2) ->
                ((String) m1.get("code")).compareTo((String) m2.get("code")));
        logger.info("【list<map>排序】 排序后：{}", JSONArray.toJSONString(list));
    }

    @Test
    public void sort2() {
        List<Map<String, Object>> list = getList();
        logger.info("【list<map>排序】 排序前：{}", JSONArray.toJSONString(list));
        list.sort(Comparator.comparing((Map map) -> (String) map.get("code"))
                .thenComparing(map -> (String) map.get("name")));
        logger.info("【list<map>排序】 排序后：{}", JSONArray.toJSONString(list));
    }

    @Test
    public void filter() {
        List<Map<String, Object>> list = getList();
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


    private List<Map<String, Object>> getList() {

        List<Map<String, Object>> list = new ArrayList<>();

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
        map3.put("code", "1");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("name", "tianjin");
        map4.put("code", "6");
        list.add(map4);

        return list;

    }


    /**
     * Arrays.asList(arr); 方法返回Arrays类的内部类ArrayList
     * 同时通过构造函数将arr的引用给内部类ArrayList的类变量
     * 内部类ArrayList重写了get set size等方法直接对arr进行操作
     * 但add remove方法没有重写 直接调用使用父类AbstractList的方法
     *
     * @author micomo
     * @date 2020/4/26 18:09
     * @link https://blog.csdn.net/u014634309/article/details/105700463
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


    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUsername("ah2");
        User user2 = new User();
        user2.setUsername("ah1");
        User user3 = new User();
        user3.setUsername("1b-c");
        User user4 = new User();
        user4.setUsername("!c");
        list.add(user);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.sort((u1, u2) ->
                {
                    char[] v1 = u1.getUsername().toCharArray();
                    char[] v2 = u2.getUsername().toCharArray();
                    int len1 = v1.length;
                    int len2 = v2.length;
                    int lim = Math.min(len1, len2);
                    int k = 0;
                    while (k < lim) {
                        char c1 = v1[k];
                        char c2 = v2[k];
                        if (c1 != c2) {
                            if (c1 >= 65 && c1 <= 122) {
                                if (c2 >= 65 && c2 <= 122) {
                                    return c1 - c2;
                                } else {
                                    return -1;
                                }
                            } else if (c1 >= 48 && c1 <= 57) {
                                if (c2 >= 65 && c2 <= 122) {
                                    return 1;
                                } else if (c2 >= 48 && c2 <= 57) {
                                    return c1 - c2;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (c2 >= 65 && c2 <= 122) {
                                    return 1;
                                } else if (c2 >= 48 && c2 <= 57) {
                                    return 1;
                                } else {
                                    return c1 - c2;
                                }
                            }
                        }
                        k++;
                    }
                    return len1 - len2;
                }
        );

        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void listToMap() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(new HashMap<String, String>(){{
            put("ip","1.1.1");
            put("port","1");
        }});
        list.add(new HashMap<String, String>(){{
            put("ip","2.2.2");
            put("port","2");
        }});
        System.out.println(list.stream().collect(Collectors.toMap(m -> m.get("ip"), m -> m.get("port"))));

    }

    @Test
    public void mergeMap() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(new HashMap<String, String>(){{
            put("name","a");
            put("score","5");
        }});
        list.add(new HashMap<String, String>(){{
            put("name","a");
            put("score","10");
        }});
        list.add(new HashMap<String, String>(){{
            put("name","b");
            put("score","10");
        }});
        list.add(new HashMap<String, String>(){{
            put("name","b");
            put("score","2");
        }});
        list.add(new HashMap<String, String>(){{
            put("name","c");
            put("score","2");
        }});
        Map<String, Integer> map = new HashMap<>();
        list.forEach(e -> {
            map.merge(e.get("name"), Integer.valueOf(e.get("score")), (a, b) -> {
                return a + b;
            });
        });
        logger.info("根据name合并计算后的map结果为：{}", JSON.toJSONString(map));
    }
}
