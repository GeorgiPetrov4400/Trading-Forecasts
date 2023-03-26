package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void initAdmin();

    void initModerator();

    void registerUser(UserServiceModel userServiceModel);

    User getUserByUsername(String username);

    User findById(Long id);

//    List<Forecast> getCurrentUserForecasts();

}
