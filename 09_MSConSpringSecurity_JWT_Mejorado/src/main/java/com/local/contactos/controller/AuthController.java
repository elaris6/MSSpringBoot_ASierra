package com.local.contactos.controller;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthController {
	
	@Value("${claveHash}")
	String claveHash;
	
	@Value("${tiempoVida}")
	long tiempoVida;

	/* En el constructor de la clase se inyecta una instancia de la clase
	 * "AuthenticationManager", para gestionar la autenticación. */
	AuthenticationManager authManager;
	public AuthController (AuthenticationManager authManager) {
		
		this.authManager = authManager;
	}
	
	/* Método para la validación de las credenciales. En el ejemplo del curso, éstas
	 * se enviaban vía query params, pero es mucho más recomendable el uso de cabeceras
	 * para estos datos sensibles. */
	@PostMapping(value = "/login")
	//public String login(@RequestParam("user") String user, @RequestParam("password") String password) {
	public String login(@RequestHeader("user") String user, @RequestHeader("password") String password) {
		
		/* Se autentica al usuario con las credenciales recibidas, mediante una instancia
		 * de la clase "Authentication" y el método ".authenticate()". */
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
		
		// Si se realiza correctamente la autenticación, se genera el token JWT
		if (auth.isAuthenticated()) {
			
			return getToken(auth);
		} else {
			
			return "Autenticación KO";
		}	
	}
	
	/* Método para construir el JWT */
	private String getToken(Authentication auth) {
		
		/* En el body del token se incluyen el usuario, sus roles, el timestamp
		 * de caducidad y los datos de la firma. */
		
		String token = Jwts.builder()
				.setIssuedAt(new Date())// Timestamp creación
				.setSubject(auth.getName()) // Usuario
				.claim("authorities", auth.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList())) // Roles. Es necesario transformar la lista devuelta por ".getAuthorities()" a una lista de otro tipo de objetos
				//.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tiempoVida))) // Timestamp expiración
				.setExpiration(new Date(System.currentTimeMillis() + tiempoVida)) // Timestamp expiración
				.signWith(SignatureAlgorithm.HS512, claveHash) // Algoritmo y clave de firma
				.compact(); // Generación del token
		
		return token;
	}
	
}
