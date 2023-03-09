package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.service.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void initAdmin();

    void initModerator();

    UserServiceModel registerUser(UserServiceModel userServiceModel);

//    UserServiceModel findByUsernameAndPassword(String username, String password);

    User getUserByUsername(String username);

//    void loginUser(Long id, String username);

    User findById(Long id);

    List<Forecast> getCurrentUserForecasts();
}
