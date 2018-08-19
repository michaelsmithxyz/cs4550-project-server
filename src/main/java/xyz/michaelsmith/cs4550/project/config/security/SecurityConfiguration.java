package xyz.michaelsmith.cs4550.project.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import xyz.michaelsmith.cs4550.project.config.security.auth.DatabaseUserDetailsService;
import xyz.michaelsmith.cs4550.project.config.security.auth.FacebookAuthenticationProvider;
import xyz.michaelsmith.cs4550.project.config.security.auth.FacebookAuthenticationSuccessHandler;
import xyz.michaelsmith.cs4550.project.config.security.auth.UnauthorizedAuthenticationEntryPoint;
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
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
            .and().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/**").authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(facebookAuthenticationProvider());
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public FacebookAuthenticationProvider facebookAuthenticationProvider() {
        return new FacebookAuthenticationProvider(userRepository);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(new DatabaseUserDetailsService(userRepository));
        daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return daoAuthenticationProvider;
    }
}
