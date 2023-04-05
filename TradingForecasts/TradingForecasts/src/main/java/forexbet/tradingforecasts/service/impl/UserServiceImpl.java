package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.UserRole;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.model.service.UserServiceModel;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.UserRoleService;
import forexbet.tradingforecasts.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsService userDetailsService;
//    private final SecurityContextRepository securityContextRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
//        this.securityContextRepository = securityContextRepository;
        this.modelMapper = modelMapper;
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
                .setRoles(userRoleService.findAll());

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
                .setRoles(List.of(moderatorRole));

        userRepository.save(moderatorUser);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        if (!userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword())) {
            throw new RuntimeException("Passwords should match");
        }

        Optional<User> findByEmail = userRepository.findByEmail(userServiceModel.getEmail());

        if (findByEmail.isPresent()) {
            throw new RuntimeException("Email is already used");
        }

        Optional<User> findByUsername = userRepository.findByUsername(userServiceModel.getUsername());

        if (findByUsername.isPresent()) {
            throw new RuntimeException("Username is already used");
        }

        UserRole normalUserRole = userRoleService.findUserRole(UserRoleEnum.User).orElseThrow();

        User normalUser = new User()
                .setEmail(userServiceModel.getEmail())
                .setUsername(userServiceModel.getUsername())
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setFirstName(userServiceModel.getFirstName())
                .setLastName(userServiceModel.getLastName())
                .setRoles(List.of(normalUserRole));

        modelMapper.map(userRepository.save(normalUser), UserServiceModel.class);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
