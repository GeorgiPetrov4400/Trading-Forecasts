package forexbet.tradingforecasts.model.service;

import forexbet.tradingforecasts.service.UserRoleService;
import forexbet.tradingforecasts.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TradingForecastsUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public TradingForecastsUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getUserByUsername(username);

        return
                new User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                                .collect(Collectors.toList())
                );
    }
}
