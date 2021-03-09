package com.sh.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteMarquee {

    public static List<Map<String, String>> list = new ArrayList<>();

    public static List<Map<String, String>> getList(){
        if (list.isEmpty()) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@");

            Map<String, String> map;

            map = new HashMap<>();
            map.put("content", "972****001 obtener $1000");
            map.put("time", "18.03.2021");
            list.add(map);

            map = new HashMap<>();
            map.put("content", "798****980 obtener $800");
            map.put("time", "26.04.2021");
            list.add(map);

            map = new HashMap<>();
            map.put("content", "787****096 obtener $800");
            map.put("time", "26.04.2021");
            list.add(map);

            map = new HashMap<>();
            map.put("content", "908****112 obtener $900");
            map.put("time", "11.05.2021");
            list.add(map);

            map = new HashMap<>();
            map.put("content", "883****901 obtener $500");
            map.put("time", "07.02.2021");
            list.add(map);
        }
        return list;
    };

}
