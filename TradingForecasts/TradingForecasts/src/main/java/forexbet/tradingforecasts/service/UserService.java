package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.service.UserServiceModel;

public interface UserService {

    void initAdmin();

    void initModerator();

    void registerUser(UserServiceModel userServiceModel);

    User getUserByUsername(String username);

    User findById(Long id);

//    boolean foundUserByEmail(String email);
//
//    boolean foundUserByUsername(String username);
}
