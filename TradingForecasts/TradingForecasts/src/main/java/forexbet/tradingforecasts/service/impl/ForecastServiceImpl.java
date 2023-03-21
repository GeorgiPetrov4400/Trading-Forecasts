package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;


    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, UserService userService, CategoryService categoryService, UserRepository userRepository) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @Override
    public void addForecast(Principal principal, ForecastAddDTO forecastAddDTO) {
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

        if (adminOptional.isPresent()) {
            Forecast forecast = modelMapper.map(forecastAddDTO, Forecast.class);
            forecast.setAdmin(userService.findById(adminOptional.get().getId()))
                    .setCategory(categoryService.findByCategoryNameEnum(forecastAddDTO.getCategory()))
                    .setForecastType(forecastAddDTO.getType());

            forecastRepository.save(forecast);
        }
    }

    @Override
    public List<Forecast> getAllActiveForecasts(long id) {
        return forecastRepository.findAllByBuyer_IdIsNullAndAdmin_IdNot(id);

//        List<Forecast> findAllForecast = forecastRepository.findAllByBuyer_IdIsNullAndAdmin_IdNot(id);
//
//        List<Forecast> activeForecasts = new ArrayList<>();
//
//        for (Forecast forecast : findAllForecast) {
//            if (forecast.getClosed() != null) {
//                activeForecasts.add(forecast);
//            }
//        }
//        return activeForecasts;
    }

    @Override
    public List<Forecast> getOwnForecastsAdded(long id) {
        return forecastRepository.findByAdmin_Id(id).stream()
                .map(forecast -> modelMapper.map(forecast, Forecast.class)).collect(Collectors.toList());
    }

    @Override
    public void buyForecast(Long id, Long currentUserId) {
        Forecast forecast = forecastRepository.findById(id).orElse(null);

        if (forecast.getClosed() != null) {
            throw new IllegalArgumentException("You cannot buy expired forecast");
            //    return;
        }

        User buyer = userRepository.findById(currentUserId).orElse(null);

        buyer.getForecasts().add(forecast);
        forecast.setBuyer(List.of(buyer));
        //  forecast.setBuyer(buyer);
        forecastRepository.saveAndFlush(forecast);
        userRepository.save(buyer);
    }

    @Override
    public void expireForecastById(Long id) {
        Forecast forecast = forecastRepository.findById(id).orElse(null);

        forecast.setClosed(LocalDateTime.now());
        forecast.setActive(false);
        forecastRepository.save(forecast);
        //  forecastRepository.deleteById(id);
    }
}
