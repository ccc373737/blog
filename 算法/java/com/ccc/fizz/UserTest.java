package com.ccc.fizz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ccc.fizz.base.utils.GsonUtil;
import com.ccc.fizz.master.base.item.entity.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	
	@Test
	public void testJson() {
		Item item = new Item();
		item.setImage("sdfdsf");
		item.setId(999L);
		item.setSellPoint("萨哈撒旦撒旦随我itUI");
		System.out.println(GsonUtil.objectToJson(item));
	}
}
