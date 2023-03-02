package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.service.UserServiceModel;

public interface UserService {

    void initAdmin();

    void initModerator();

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(Long id, String username);
}
