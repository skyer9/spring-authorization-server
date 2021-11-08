package sample.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DefaultSecurityConfigTest {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void createUser() {
//		userDetailsService.
//
//		userDetailsService.save(User.builder()
//				.uid("user")
//				.password(passwordEncoder.encode("pass"))
//				.name("user")
//				.email("skyer9@gmail.com")
//				.roles(Collections.singletonList("ROLE_USER"))
//				.build());
	}
}
