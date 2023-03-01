package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.UserRoleService;
import forexbet.tradingforecasts.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initAdmin() {
        var adminRole = userRoleService.findUserRole(UserRoleEnum.Admin).orElseThrow();

        var adminUser = new User()
                .setEmail("admin@example.com")
                .setUsername("Admin")
                .setPassword(passwordEncoder.encode("12345"))
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setUserRole(adminRole);

        userRepository.save(adminUser);
    }

    @Override
    public void initModerator() {
        var moderatorRole = userRoleService.findUserRole(UserRoleEnum.Moderator).orElseThrow();

        var moderatorUser = new User()
                .setEmail("moderator@example.com")
                .setUsername("Moderator")
                .setPassword(passwordEncoder.encode("12345"))
                .setFirstName("Moder")
                .setLastName("Moderatorov")
                .setUserRole(moderatorRole);

        userRepository.save(moderatorUser);

    }
}
