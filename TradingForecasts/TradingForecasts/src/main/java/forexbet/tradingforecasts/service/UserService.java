package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.view.UserViewModel;

public interface UserService {

    void initAdmin();

    void initModerator();

    void registerUser(UserViewModel userViewModel);

    User getUserByUsername(String username);

    User findById(Long id);

//    boolean foundUserByEmail(String email);
//
//    boolean foundUserByUsername(String username);
}
