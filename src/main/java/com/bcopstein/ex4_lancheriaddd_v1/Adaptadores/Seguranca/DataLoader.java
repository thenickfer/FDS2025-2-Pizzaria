package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Seguranca;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;


@Configuration
public class DataLoader {
  @Bean
  CommandLineRunner initUsers(DataSource dataSource, PasswordEncoder encoder) {
    return args -> {
      JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);

      if (!jdbc.userExists("user")) {
        UserDetails user = User.withUsername("user")
            .password(encoder.encode("user123"))
            .roles("USER")
            .build();
        jdbc.createUser(user);
      }

      if (!jdbc.userExists("master")) {
        UserDetails master = User.withUsername("master")
            .password(encoder.encode("master123"))
            .roles("MASTER")
            .build();
        jdbc.createUser(master);
      }
    };
  }
}