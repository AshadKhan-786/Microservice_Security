package in.ashokit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.UserCredential;

public interface UserCredentialRepo extends JpaRepository<UserCredential, Integer>{

	public Optional<UserCredential> findByName(String uname);
}
