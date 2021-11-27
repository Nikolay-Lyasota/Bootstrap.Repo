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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import web.dao.UserDaoData;
import web.hanlder.SuccessHandler;
import web.model.User;
import web.service.UserService;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessHandler successHandler;
    private final UserDetailsService userDetailsService;
    private final UserDaoData repository;
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
                .antMatchers("/registration", "/all", "/getPrincipal").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/user").hasAuthority("USER")
                .antMatchers("/user", "/admin").authenticated()
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")

                .successHandler(successHandler)
                .and()
                .logout()
                .and()
                .oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(oauthUserService());
                    });
                }).oauth2Login().redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/oauth2/authorization/google"));
//                .formLogin().loginPage("/login")
//                .successHandler(successHandler)
    }

    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService() {
        DefaultOAuth2UserService oauthService = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User oAuth2User = oauthService.loadUser(request);
            Map<String, Object> attributes = oAuth2User.getAttributes();
            User user = new User();
            user.setEmail((String) attributes.get("email"));
            user.setName((String) attributes.get("name"));
            repository.save(user);
            return oAuth2User;
        };
    }

}
