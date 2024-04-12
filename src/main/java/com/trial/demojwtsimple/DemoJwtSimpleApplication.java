package com.trial.demojwtsimple;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class DemoJwtSimpleApplication {

	public static void main(String[] args) {
		Map<String, Object> claims= new HashMap<String, Object>();
		String jwtGenerated = tokenCreate(claims,"Saba");
		System.out.println("The Generated JWT : "+ jwtGenerated  );
		System.out.println("The Extracted Username : "+ extractedClaims(jwtGenerated, Claims::getSubject) );
		System.out.println(" The Expiration Date : "+ extractedClaims(jwtGenerated, Claims::getExpiration));
	}

	

	private static String tokenCreate(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1800))
				.signWith(SignatureAlgorithm.HS256,"S3cr3t")
				.compact();
	}
	private static <T> T extractedClaims(String jwtGenerated, Function<Claims,T> claimsResolver) {
		final Claims claims = Jwts.parser().setSigningKey("S3cr3t").parseClaimsJws(jwtGenerated).getBody();
		return claimsResolver.apply(claims);
	}

}
