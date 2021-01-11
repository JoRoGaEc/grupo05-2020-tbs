package com.sif.digestyc.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestUser {

	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("user"));
	}

}
