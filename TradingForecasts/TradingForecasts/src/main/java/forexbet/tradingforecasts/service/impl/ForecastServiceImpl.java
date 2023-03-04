package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.service.ForecastServiceModel;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserService userService;
    private final CategoryService categoryService;


    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, CurrentUser currentUser, UserService userService, CategoryService categoryService) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void addForecast(ForecastServiceModel forecastServiceModel) {
        Forecast forecast = modelMapper.map(forecastServiceModel, Forecast.class);
        forecast.setAdmin(userService.findById(currentUser.getId()))
                .setCategory(categoryService.findByCategoryNameEnum(forecastServiceModel.getCategory()))
                .setForecastType(forecastServiceModel.getForecastType());

        forecastRepository.save(forecast);
    }
}
