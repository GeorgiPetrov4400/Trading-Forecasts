//package forexbet.tradingforecasts.model.service;
//
//import forexbet.tradingforecasts.model.entity.User;
//import forexbet.tradingforecasts.model.entity.UserRole;
//import forexbet.tradingforecasts.repository.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.List;
//
//public class ApplicationUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public ApplicationUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findUserByEmail(username)
//                .map(this::map).orElseThrow(() -> new UsernameNotFoundException(username + " not found!"));
//    }
//
//    private UserDetails map(User user) {
//        return new User(user.getEmail(), user.getPassword(), extractAuthorities(user));
//    }
//
//    private List<GrantedAuthority> extractAuthorities(User user) {
//        return user.getRoles().stream().map(this::mapRole).toList();
//    }
//
//    private GrantedAuthority mapRole(UserRole userRole) {
//        return new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name());
//    }
//}
