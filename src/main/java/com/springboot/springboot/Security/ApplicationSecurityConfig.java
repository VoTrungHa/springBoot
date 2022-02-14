package com.springboot.springboot.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.lang.reflect.Method;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    //DI
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()// permit user accept into this paths url
//                .antMatchers(("/api/**")).denyAll()// deny accept path url
                // must login with role (...)
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())// ONE

                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMIN1.name())
                // any login with role (...) could accept
//                .antMatchers("/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.STUDENT.name())// Multi
                //any request must authentication
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();// show from login with http basic
    }

    // method userDetailsService  effective provide info user, this things help for us setting info of user easy
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123123"))
                .authorities(ApplicationUserRole.STUDENT.grantedAuthorities())
//                .roles(ApplicationUserRole.STUDENT.name())
                .build();

        UserDetails admin = User.builder()//read
                .username("admin")
                .password(passwordEncoder.encode("123123"))
                .authorities(ApplicationUserRole.ADMIN.grantedAuthorities())
//                .roles(ApplicationUserRole.ADMIN.name())
                .build();
        UserDetails admin1 = User.builder()// read
                .username("admin1")
                .password(passwordEncoder.encode("123123"))
                .authorities(ApplicationUserRole.ADMIN1.grantedAuthorities())
//                .roles(ApplicationUserRole.ADMIN1.name())
                .build();

        return new InMemoryUserDetailsManager(
                user,admin,admin1
        );

//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
//                        .username("user")
//                        .password(passwordEncoder.encode("123123"))
//                        .roles("USER") // phân quyền là người dùng.
//                        .build()
//        );
//        manager.createUser(
//                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
//                        .username("admin")
//                        .password(passwordEncoder.encode("123123"))
//                        .roles("ADMIN") // phân quyền là người dùng.
//                        .build()
//        );
//        return manager;

    }
}
