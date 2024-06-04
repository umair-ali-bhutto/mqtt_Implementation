package com.ag.generic.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {

	private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
	private static final String DIGIT = "0123456789";
	private static final String SPECIAL_SYMBOLS = "+-@";
	private static final int PASSWORD_LENGTH = 8;
	private static SecureRandom random = new SecureRandom();

	public static String generateStrongPassword() {

		StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

		
		String strLowerCase = generateRandomString(CHAR_LOWERCASE, 3);
		result.append(strLowerCase);

		
		String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 1);
		result.append(strUppercaseCase);

		String strDigit = generateRandomString(DIGIT,3);
		result.append(strDigit);

		String strSpecialChar = generateRandomString(SPECIAL_SYMBOLS, 1);
		result.append(strSpecialChar);

		String password = result.toString();
		

		return shuffleString(password);
	}

	private static String generateRandomString(String input, int size) {

		if (input == null || input.length() <= 0)
			throw new IllegalArgumentException("Invalid input.");
		if (size < 1)
			throw new IllegalArgumentException("Invalid size.");

		StringBuilder result = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			int index = random.nextInt(input.length());
			result.append(input.charAt(index));
		}
		return result.toString();
	}

 
	public static String shuffleString(String input) {
		List<String> result = Arrays.asList(input.split(""));
		Collections.shuffle(result);
		return result.stream().collect(Collectors.joining());
	}

}
