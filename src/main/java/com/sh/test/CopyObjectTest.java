package com.sh.test;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.sh.entity.Goods;
import com.sh.entity.Order;
import com.sh.excel.BizGoods;
import com.sh.excel.BizOrder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CopyObjectTest {

    public static void main(String[] args) {
        Goods goods = new Goods();
        goods.setName("abc");
        Goods goods1 = new Goods();
        goods1.setName("xyz");
        List<Goods> list = new ArrayList<Goods>(){{
            add(goods);
            add(goods1);
        }};
        Order order = new Order();
        order.setName("mico");
        order.setGoods(goods);
//        order.setList(list);
        System.out.println(JSON.toJSONString(order));
        BizOrder bizOrder = new BizOrder();
        BeanUtils.copyProperties(order, bizOrder);
        System.out.println(JSON.toJSONString(bizOrder));
//        bizOrder.setList(order.getList().stream().map(g -> {
//            BizGoods bg = new BizGoods();
//            BeanUtils.copyProperties(g, bg);
//            return bg;
//        }).collect(Collectors.toList()));
        System.out.println(bizOrder.getGoods().getName());
        System.out.println(bizOrder.getList().get(0).getName());
    }
}
