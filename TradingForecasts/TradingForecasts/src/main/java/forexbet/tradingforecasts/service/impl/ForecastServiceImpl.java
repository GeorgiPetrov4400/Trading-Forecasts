package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.service.ForecastServiceModel;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;


    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, CurrentUser currentUser, UserService userService, CategoryService categoryService, UserRepository userRepository) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @Override
    public void addForecast(ForecastServiceModel forecastServiceModel) {
        Forecast forecast = modelMapper.map(forecastServiceModel, Forecast.class);
        forecast.setAdmin(userService.findById(currentUser.getId()))
                .setCategory(categoryService.findByCategoryNameEnum(forecastServiceModel.getCategory()))
                .setForecastType(forecastServiceModel.getForecastType());

        forecastRepository.save(forecast);
    }

    @Override
    public List<Forecast> getAllActiveForecasts(long id) {
        return forecastRepository.findAllByBuyer_IdIsNullAndAdmin_IdNot(id);
    }

    @Override
    public void buyForecast(Long id, Long currentUserId) {
        Forecast forecast = forecastRepository.findById(id).orElse(null);

        User buyer = userRepository.findById(currentUserId).orElse(null);

        buyer.getForecasts().add(forecast);
        forecast.setBuyer(buyer);
        forecastRepository.saveAndFlush(forecast);
        userRepository.save(buyer);
    }

    @Override
    public void removeForecastById(Long id) {
        forecastRepository.deleteById(id);
    }
}
