package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.bindings.AuthRequest;
import in.ashokit.bindings.AuthResponse;
import in.ashokit.entity.UserCredential;
import in.ashokit.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/register")
	public ResponseEntity<String> getRegister(@RequestBody UserCredential user) {
		
		String msg = authService.saveUser(user);
		
		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		
		AuthResponse response = new AuthResponse();
		
		try {
			Authentication authenticate = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			if(authenticate.isAuthenticated()) {
				String token = authService.generateToken(request.getUsername());
				
				response.setToken(token);
				response.setLoginValid("Yes");
}
		} catch (Exception e) {
			response.setToken("");
			response.setLoginValid("No");
		}
		
		return response;
	}
	
	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		authService.validateToken(token);
		return "Token is valid";
	}
	
}
