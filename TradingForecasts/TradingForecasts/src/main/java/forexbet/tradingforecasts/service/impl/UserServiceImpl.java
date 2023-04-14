package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.dto.ChangeAccountRoleDTO;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.UserRole;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.model.view.UserViewModel;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.UserRoleService;
import forexbet.tradingforecasts.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    //  private final String defaultAdminPass;
    //  private final String defaultModeratorPass;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService,
                           PasswordEncoder passwordEncoder,
                           //  @Value("${TradingForecasts.Admin.defaultPass}") String defaultAdminPass,
                           //    @Value("${TradingForecasts.Moderator.defaultPass}") String defaultModeratorPass,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        //   this.defaultAdminPass = defaultAdminPass;
        //    this.defaultModeratorPass = defaultModeratorPass;
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
    public void registerUser(UserViewModel userViewModel) {
        if (!userViewModel.getPassword().equals(userViewModel.getConfirmPassword())) {
            throw new RuntimeException("Passwords should match");
        }

        Optional<User> findByEmail = userRepository.findByEmail(userViewModel.getEmail());

        if (findByEmail.isPresent()) {
            throw new RuntimeException("Email is already used");
        }

        Optional<User> findByUsername = userRepository.findByUsername(userViewModel.getUsername());

        if (findByUsername.isPresent()) {
            throw new RuntimeException("Username is already used");
        }

        UserRole normalUserRole = userRoleService.findUserRole(UserRoleEnum.User).orElseThrow();

        User normalUser = new User()
                .setEmail(userViewModel.getEmail())
                .setUsername(userViewModel.getUsername())
                .setPassword(passwordEncoder.encode(userViewModel.getPassword()))
                .setFirstName(userViewModel.getFirstName())
                .setLastName(userViewModel.getLastName())
                .setRoles(List.of(normalUserRole));

        modelMapper.map(userRepository.save(normalUser), UserViewModel.class);
    }

//    @Override
//    public boolean foundUserByEmail(String email) {
//        return this.userRepository.findByEmail(email).isPresent();
//    }
//
//    @Override
//    public boolean foundUserByUsername(String username) {
//        return this.userRepository.findByUsername(username).isPresent();
//    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void saveUserChanges(User changeUsername) {
        userRepository.save(changeUsername);
    }

    @Override
    public void changeUserRole(Long id, ChangeAccountRoleDTO changeAccountRoleDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();

        List<UserRole> roles = userOptional.get().getRoles();

        if (changeAccountRoleDTO.getNewRole().equals("Admin")) {
            userOptional.get().setRoles(List.of(roles.get(0)));
        } else if (changeAccountRoleDTO.getNewRole().equals("Moderator")) {
            userOptional.get().setRoles(List.of(roles.get(1)));
        } else {
            userOptional.get().setRoles(List.of(roles.get(2)));
        }

        userRepository.saveAndFlush(user);

    }
}
