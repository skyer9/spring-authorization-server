/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.util.Collection;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

	private final DataSource dataSource;

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public DefaultSecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// @formatter:off
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorizeRequests ->
						authorizeRequests.anyRequest().authenticated()
				)
				.formLogin(withDefaults());
		return http.build();
	}
	// @formatter:on

	// @formatter:off
	@Bean
	UserDetailsService users() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

		try {
			UserDetails user = jdbcUserDetailsManager.loadUserByUsername("user1");
		} catch (UsernameNotFoundException ex) {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			UserDetails user = User
					.withUsername("user1")
					.passwordEncoder(encoder::encode)
					.password("password")
					.roles("USER")
					.build();
			jdbcUserDetailsManager.createUser(user);
		}

		return jdbcUserDetailsManager;
	}
	// @formatter:on
}
