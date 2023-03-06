package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void initAdmin();

    void initModerator();

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(Long id, String username);

    User findById(Long id);

    List<Forecast> getCurrentUserForecasts();
}
