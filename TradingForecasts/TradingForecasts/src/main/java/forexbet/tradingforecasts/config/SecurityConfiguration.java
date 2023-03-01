package forexbet.tradingforecasts.config;

import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
//import forexbet.tradingforecasts.model.service.ApplicationUserDetailsService;
import forexbet.tradingforecasts.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .requestMatchers("/", "/users/login", "/users/register", "/users/login-error").permitAll()
//                .requestMatchers("/pages/moderators").hasRole(UserRoleEnum.Moderator.name())
//                .requestMatchers("/pages/admins").hasRole(UserRoleEnum.Admin.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/users/login")
//                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
//                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
//                .defaultSuccessUrl("/").failureForwardUrl("/users/login-error");
//
//        return httpSecurity.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        return new ApplicationUserDetailsService(userRepository);
//    }
}
