package com.ccc.fizz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.ccc.fizz.base.constant.FizzConstant;
import com.ccc.fizz.base.utils.GsonUtil;
import com.ccc.fizz.master.base.user.entity.UserCart;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Test
	public void addRedis() {
		/*UserCart u1 = new UserCart();
		u1.setPrice(55656l);
		u1.setItemImage("dsfdsfds");
		u1.setItemTitle("dsfsdgfdgfdsgfdg");
		u1.setNum(5);

		redisTemplate.opsForHash().put("3737", "aaa", "111");
		redisTemplate.opsForHash().put("3737", "1253", GsonUtil.objectToJson(u1));
		String msg = (String) redisTemplate.opsForHash().get("3737", "1253");
		UserCart u2 = GsonUtil.jsonToOject("", UserCart.class);
		System.out.println(u2);*/
		
		//redisTemplate.delete("3737");
		System.out.println("**********************************");
		System.out.println(redisTemplate.opsForValue().setIfAbsent("aaa", "safsdgsdg"));


	}

}
