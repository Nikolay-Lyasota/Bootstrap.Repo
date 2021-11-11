package web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.x509.X509PrincipalExtractor;
import web.hanlder.SuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final SuccessHandler successHandler;
    private final UserDetailsService userDetailsService;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration","/all","/getPrincipal").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/user").hasAuthority("USER")
                .antMatchers("/user","/admin").authenticated()
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")

                .and()
                .oauth2Login().loginPage("/login")

                .successHandler(successHandler)
//                .formLogin().loginPage("/login")
//                .successHandler(successHandler)
                .and()
                .logout();
    }

}
