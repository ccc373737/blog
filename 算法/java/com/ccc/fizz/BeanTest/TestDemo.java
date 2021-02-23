package com.ccc.fizz.BeanTest;

import com.ccc.fizz.master.base.item.entity.Item;
import com.ccc.fizz.master.base.item.entity.ItemCopy;
import com.ccc.fizz.master.base.item.entity.ItemDes;
import com.ccc.fizz.master.base.item.entity.ItemDesCopy;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDemo {
    public static void main(String[] args) throws Exception{
        Item item = new Item();
        item.setId(10l);
        item.setSellPoint("aaaaa");
        item.setNum(666);
        item.setPrice(999l);
        item.setTitle("sadsajdks");

        List<ItemDes> list = new ArrayList<>();
        ItemDes d1 = new ItemDes();
        d1.setItemId(1l);
        d1.setItemDesc("qqqqd1");
        d1.setUpdated(new Date());

        ItemDes d2 = new ItemDes();
        d2.setItemId(2l);
        d2.setItemDesc("wwwww2");
        d2.setUpdated(new Date());
        list.add(d1);
        list.add(d2);

        item.setItemDesList(list);

        ItemCopy itemCopy = new ItemCopy();

        /*Method m = itemCopy.getClass().getMethod("setItemDesList", List.class);
        m.invoke(itemCopy, list);
        System.out.println(itemCopy);*/


        PropertyUtils.copyProperties(itemCopy, item);

        //List<ItemDesCopy> itemDesList = itemCopy.getItemDesList();

        System.out.println(itemCopy);
    }

}
