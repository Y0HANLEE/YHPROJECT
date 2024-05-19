package org.project.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PwGenerator {
	public static void main(String[] args) {
		int i = 0;
		while (i < 10) {
			String password = "1234";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);

			System.out.println(hashedPassword);
			i++;
		}
	}
}
