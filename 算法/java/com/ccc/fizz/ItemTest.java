package com.ccc.fizz;

import java.util.List;

import javax.annotation.Resource;

import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ccc.fizz.master.base.item.entity.Item;
import com.ccc.fizz.master.manager.dao.ItemManagerMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTest {
	@Resource
	private ItemManagerMapper itemManagerMapper;

	
	@Test
	public void getItem() {

		//zk.delete("aaa", 2);

		List<Item> items = itemManagerMapper.getItemsByPage(10, 10);
		System.out.println(items);
	}
}
