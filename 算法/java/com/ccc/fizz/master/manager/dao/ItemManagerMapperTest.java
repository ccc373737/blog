package com.ccc.fizz.master.manager.dao;

import com.ccc.fizz.FizzApplication;
import com.ccc.fizz.master.base.item.entity.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FizzApplication.class)
public class ItemManagerMapperTest {

    @Autowired(required = false)
    private ItemManagerMapper itemManagerMapper;

    @Test
    public void insertTest() {
        Item item = new Item();
        item.setSellPoint("aaaaa");
        item.setNum(666);
        item.setPrice(999l);
        item.setTitle("sadsajdks");
        //此时id为null
        System.out.println(item.getId());
        System.out.println(itemManagerMapper.insertItem(item));
        //插入之后item.id自动赋值为自增的id，注意只对主键生效，其他字段不会自动赋值
        System.out.println(itemManagerMapper.getItemById(item.getId()));
    }

    @Test
    public void deletetTest() {
        System.out.println(itemManagerMapper.deleteItem(8L));
    }
}