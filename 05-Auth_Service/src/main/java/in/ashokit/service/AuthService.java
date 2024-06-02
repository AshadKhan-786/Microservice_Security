package in.ashokit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.ashokit.entity.UserCredential;
import in.ashokit.repo.UserCredentialRepo;

@Service
public class AuthService {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private UserCredentialRepo crepo;

	public String saveUser(UserCredential user) {
		
		Optional<UserCredential> byName = crepo.findByName(user.getName());
		
		if(byName.isPresent()) {
			UserCredential userCredential = byName.get();
			return "Username "+ userCredential.getName() + " already exists in system";
		}
		
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		crepo.save(user);
		
		return "User added to the system";
	
	}
	
	public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}