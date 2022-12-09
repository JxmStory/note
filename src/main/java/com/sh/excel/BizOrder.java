package com.sh.excel;

import java.util.List;

public class BizOrder {
    private String name;
    private List<BizGoods> list;
    private BizGoods goods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BizGoods> getList() {
        return list;
    }

    public void setList(List<BizGoods> list) {
        this.list = list;
    }

    public BizGoods getGoods() {
        return goods;
    }

    public void setGoods(BizGoods goods) {
        this.goods = goods;
    }
}
