package com.sh.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CollectionUtil {

    /**
     * 将map切成段，作用与PHP的array_chunk函数相当
     *
     * @param chunkMap 被切段的map
     * @param chunkNum 每段的大小
     * @param <k>      map的key类型
     * @param <v>      map的value类型 如果是自定义类型，则必须实现equals和hashCode方法
     * @return
     */
    public static <k, v> List<Map<k, v>> mapChunk(Map<k, v> chunkMap, int chunkNum) {
        if (chunkMap == null || chunkNum <= 0) {
            List<Map<k, v>> list = new ArrayList<>();
            list.add(chunkMap);
            return list;
        }
        Set<k> keySet = chunkMap.keySet();
        Iterator<k> iterator = keySet.iterator();
        int i = 1;
        List<Map<k, v>> total = new ArrayList<>();
        Map<k, v> tem = new HashMap<>();
        while (iterator.hasNext()) {
            k next = iterator.next();
            tem.put(next, chunkMap.get(next));
            if (i == chunkNum) {
                total.add(tem);
                tem = new HashMap<>();
                i = 0;
            }
            i++;
        }
        if(!CollectionUtils.isEmpty(tem)){
            total.add(tem);
        }
        return total;
    }


    /**
     * 将list切割
     *
     * @param chunkList 被分隔的数组
     * @param chunkNum  每段的大小
     * @param <T>       List中的类型
     * @return
     */
    public static <T> List<List<T>> listChunk(List<T> chunkList, int chunkNum) {
        if (chunkList == null || chunkNum <= 0) {
            List<List<T>> t = new ArrayList<>();
            t.add(chunkList);
            return t;
        }
        Iterator<T> iterator = chunkList.iterator();
        int i = 1;
        List<List<T>> total = new ArrayList<>();
        List<T> tem = new ArrayList<>();
        while (iterator.hasNext()) {
            T next = iterator.next();
            tem.add(next);
            if (i == chunkNum) {
                total.add(tem);
                tem = new ArrayList<>();
                i = 0;
            }
            i++;
        }
        if(!CollectionUtils.isEmpty(tem)){
            total.add(tem);
        }
        return total;
    }


    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        int i = 1;
//        while (i <= 100) {
//            map.put("a" + i, "b" + i);
//            i++;
//        }
//        List<Map<String, String>> list = mapChunk(map, 30);
//        System.out.println(list.size());
//        for (Map<String, String> m : list) {
//            System.out.println("==================" + m.size());
//            System.out.println(m);
//        }
        List<String> list = new ArrayList<>();
        list.add("easy");
        list.add("bbb");
        System.out.println(list.contains("Easy"));
    }
}
