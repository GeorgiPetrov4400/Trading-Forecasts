package forexbet.tradingforecasts.config;

import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
//import forexbet.tradingforecasts.model.service.ApplicationUserDetailsService;
import forexbet.tradingforecasts.model.service.TradingForecastsUserDetailsService;
import forexbet.tradingforecasts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    private final UserService userService;
//
//    @Autowired
//    public SecurityConfiguration(UserService userService) {
//        this.userService = userService;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/", "/users/register", "/users/login", "/about", "/users/login-error").permitAll()
                //       .requestMatchers("/users/register", "/users/login", "/about", "/users/login-error").anonymous()
                .requestMatchers("/about", "/contact", "/orders/order", "/freeForecasts").authenticated()
                .requestMatchers("/forecasts/add").hasRole(UserRoleEnum.Moderator.name())
                .requestMatchers("/admin", "/forecasts/add").hasRole(UserRoleEnum.Admin.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .permitAll()
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/users/login-error")
                .and().logout().logoutUrl("/users/logout")
                .logoutSuccessUrl("/").invalidateHttpSession(true);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new TradingForecastsUserDetailsService(userService);
//    }
}
