package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.model.service.UserServiceModel;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.UserRoleService;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.util.CurrentUser;
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
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
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
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        var normalUserRole = userRoleService.findUserRole(UserRoleEnum.User).orElseThrow();

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

        var normalUser = new User()
                .setEmail(userServiceModel.getEmail())
                .setUsername(userServiceModel.getUsername())
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setFirstName(userServiceModel.getFirstName())
                .setLastName(userServiceModel.getLastName())
                .setRoles(List.of(normalUserRole));


//        User user = modelMapper.map(userServiceModel, User.class);
//
//        user.setUserRole(normalUserRole);

        return modelMapper.map(userRepository.save(normalUser), UserServiceModel.class);
    }

//    @Override
//    public UserServiceModel findByUsernameAndPassword(String username, String password) {
//        return userRepository.findByUsernameAndPassword(username, password)
//                .map(user -> modelMapper.map(user, UserServiceModel.class)).orElse(null);
//    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

//    @Override
//    public void loginUser(Long id, String username) {
//        currentUser.setId(id);
//        currentUser.setUsername(username);
//    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<Forecast> getCurrentUserForecasts() {
        return userRepository.findByUsername(currentUser.getUsername()).get().getForecasts();
    }
}
