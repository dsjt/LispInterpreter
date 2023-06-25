package com.github.dsjt.lispinterpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

class LispInterpreterTest {

	private LispInterpreter lispInterpreter;
	
	@BeforeEach	
	protected void setUp() {
		lispInterpreter = new LispInterpreter();
	}

//	@Test
//	void testParseInput() {
//		fail("まだ実装されていません");
//	}

	@Test
	void testTokenizeInput01() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method;
		method = LispInterpreter.class.getDeclaredMethod("tokenizeInput", String.class);
		method.setAccessible(true);
		Object res = method.invoke(lispInterpreter, "(+ 1 2 3)");
		List<String> actual = (List<String>) res;
		List<String> expected= Arrays.asList("(", "+", "1", "2", "3", ")");
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testTokenizeInput02() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method;

		method = LispInterpreter.class.getDeclaredMethod("tokenizeInput", String.class);
		method.setAccessible(true);
		String tc = "(+ (+ 2 3) 1)";
		List<String> actual = (List<String>) method.invoke(lispInterpreter, tc);
		assertEquals(actual.size(), 9);
		
		StringBuilder sb = new StringBuilder();
		String pre = "";
		for (String a : actual) {
			if (!pre.equals("") && !pre.equals("(") && !a.equals(")")) {
				sb.append(" ");
			}
			sb.append(a);
			pre = a;
		}
		assertEquals(tc, sb.toString());
	}
	
//	@Test
//	void testBuildParseTree() {
//		fail("まだ実装されていません");
//	}

}
