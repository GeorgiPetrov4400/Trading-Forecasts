package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.dto.UserRegisterDTO;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.UserRole;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.model.service.UserServiceModel;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.UserRoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserRoleService mockUserRoleService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private ModelMapper mockModelMapper;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private UserServiceImpl toTest;

    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(mockUserRepository, mockUserRoleService, mockPasswordEncoder, mockModelMapper);
    }

    @Test
    void testInitAdmin() {
        UserRegisterDTO mockAdminUser = new UserRegisterDTO()
                .setEmail("test@example.com")
                .setUsername("Test")
                .setPassword("12345")
                .setConfirmPassword("12345")
                .setFirstName("Test")
                .setLastName("Testov");
    }

    @Test
    void testUserRegister() {

        UserRegisterDTO testUserRegisterDTO = new UserRegisterDTO()
                .setEmail("test@example.com")
                .setUsername("Test")
                .setPassword("12345")
                .setConfirmPassword("12345")
                .setFirstName("Test")
                .setLastName("Testov");

//        UserServiceModel testUserServiceModel = new UserServiceModel()
//                .setEmail("test@example.com")
//                .setUsername("Test")
//                .setPassword("12345")
//                .setConfirmPassword("12345")
//                .setFirstName("Tester")
//                .setLastName("Testov")
//                .setUserRole("User");

        toTest.registerUser(mockModelMapper.map(mockUserRepository.save(testUserRegisterDTO), UserServiceModel.class));

        Mockito.verify(mockUserRepository).save(any());
    }

    @Test
    void testUserRegister2() {
//        String testPassword = "12345";
        String encodedPassword = "encoded_password";

        User testUserServiceModel = new User()
                .setEmail("test@example.com")
                .setUsername("Test")
                .setPassword(encodedPassword)
                .setFirstName("Tester")
                .setLastName("Testov");

        when(mockPasswordEncoder.encode(testUserServiceModel.getPassword())).thenReturn(encodedPassword);

        mockModelMapper.map(mockUserRepository.save(testUserServiceModel), UserServiceModel.class);

        Mockito.verify(mockUserRepository).save(userArgumentCaptor.capture());

        User actualSavedUser = userArgumentCaptor.getValue();
        Assertions.assertEquals(testUserServiceModel.getEmail(), actualSavedUser.getEmail());
        Assertions.assertEquals(encodedPassword, actualSavedUser.getPassword());
    }
}
