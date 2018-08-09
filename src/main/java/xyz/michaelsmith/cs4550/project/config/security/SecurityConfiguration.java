package xyz.michaelsmith.cs4550.project.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import xyz.michaelsmith.cs4550.project.user.data.UserRepository;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final FacebookAuthenticationSuccessHandler facebookAuthenticationSuccessHandler;

    @Autowired
    public SecurityConfiguration(UserRepository userRepository, FacebookAuthenticationSuccessHandler facebookAuthenticationSuccessHandler) {
        this.userRepository = userRepository;
        this.facebookAuthenticationSuccessHandler = facebookAuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .headers()
                .frameOptions().disable()
            .and().exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint())
            .and().formLogin()
                .successHandler(facebookAuthenticationSuccessHandler)
            .and().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/**").authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FacebookAuthenticationProvider(userRepository);
    }
}
