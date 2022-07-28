package com.sh;

import com.sh.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteApplicationTests {

	@Autowired
	private UserDao userDao;

	@Test
	public void contextLoads() {
	}

	@Test
	public void keyMap() {
		Map map = userDao.getMap();
		Iterator iterator = map.keySet().iterator();
		if (iterator.hasNext())
			System.out.println(map.get(iterator.next()));
	}

	@Test
	public void listMap() {
		List<Map<String, String>> list = userDao.listMap();
		list.forEach(e -> {
			System.out.println("+" + e.get("PHONE"));
			System.out.println("-" + e.get("phone"));
		});

	}
}
