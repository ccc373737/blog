package com.ccc.fizz;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ccc.fizz.master.base.user.entity.UserCart;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class FizzApplicationTests {

	@SuppressWarnings("unused")
	@Test
	public void TestDemo() {

		List<UserCart> carts = new ArrayList<>();
		for (UserCart userCart : carts) {
			System.out.println(userCart.getId());
		}
	}
}
