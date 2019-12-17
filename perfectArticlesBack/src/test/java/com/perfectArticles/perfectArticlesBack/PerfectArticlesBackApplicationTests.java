package com.perfectArticles.perfectArticlesBack;

import com.perfectArticles.perfectArticlesBack.util.ArticleUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PerfectArticlesBackApplicationTests {

	@Test
	void contextLoads() {
	}

	//**************************************************************************
	//Tag Tests
	//**************************************************************************

	@Test
	void test000010TagsTest() {

		//5 eggs, 3 amazons all with things that should be removed, 2 garrett 1 cap, losts of removed words
		String testText = "this it is Perfect GARRETT egg. egg i i  i i i egg egg amazon; garrett " +
				"it then and i i i EGG it it it it amazon. amazon//";

		List<String> tagsReturned = ArticleUtil.generateArticleTags(testText);

		assert  (tagsReturned.get(0).equals("egg"));
		assert  (tagsReturned.get(1).equals("amazon"));
		assert  (tagsReturned.get(2).equals("garrett"));
	}

	@Test
	void test000020TagsEmpty() {

		String testText = "";

		List<String> tagsReturned = ArticleUtil.generateArticleTags(testText);

		assert  (tagsReturned.size() == 0);
	}

	@Test
	void test000030TagsSemiFull() {

		//5 eggs, 3 amazons all with things that should be removed, 2 garrett 1 cap, losts of removed words
		String testText = "Perfect this is a and";

		List<String> tagsReturned = ArticleUtil.generateArticleTags(testText);

		assert  (tagsReturned.size() == 1);
		assert  (tagsReturned.get(0).equals("perfect"));
	}

}
