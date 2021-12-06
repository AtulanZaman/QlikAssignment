package com.example.messages;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MessagesApplicationTests {

	@MockBean
	private MessageRepository repository;

	@Test
	void testMessageIsPalindrome() {
		assertTrue(new Message("abcba").getIsPalindrome());
		assertTrue(new Message("abba").getIsPalindrome());
		assertTrue(new Message("c").getIsPalindrome());
		assertFalse(new Message("abc").getIsPalindrome());
	}


}
