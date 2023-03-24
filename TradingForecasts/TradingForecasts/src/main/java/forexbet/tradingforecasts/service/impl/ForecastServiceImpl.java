package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
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
                    .setForecastType(forecastAddDTO.getType())
                    .setDescription(forecastAddDTO.getDescription())
                    .setPictureUrl(forecastAddDTO.getPictureUrl())
                    .setPrice(forecastAddDTO.getPrice())
                    .setActive(true);

            forecastRepository.save(forecast);
        }
    }

    @Override
    public List<ForecastDTO> getUserBoughtForecasts(Principal principal) {
        Optional<User> buyerOptional = userRepository.findByUsername(principal.getName());

        return buyerOptional.map(user -> forecastRepository.findAllByBuyer_IdIsNullAndAdmin_IdNot(user.getId())
                .stream().map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
                .collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<ForecastDTO> getOwnForecastsAdded(Principal principal) {
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

        return adminOptional.map(user -> forecastRepository.findByAdmin_Id(user.getId()).stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
                .collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<ForecastDTO> getActiveForecasts() {
        return forecastRepository.findAllByClosedIsNullOrderByCreatedDesc().stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<ForecastDTO> getExpiredForecasts() {
        return forecastRepository.findAllByClosedIsNotNullOrderByClosedDesc().stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void buyForecast(Long id, Principal principal) {
        Forecast forecast = forecastRepository.findById(id).orElse(null);

        if (forecast.getClosed() != null) {
            throw new IllegalArgumentException("You cannot buy expired forecast");
        }

        Optional<User> buyerOptional = userRepository.findByUsername(principal.getName());

        User buyer = userRepository.findById(buyerOptional.get().getId()).orElse(null);

        buyer.getForecasts().add(forecast);
        forecast.setBuyer(List.of(buyer));
        forecastRepository.saveAndFlush(forecast);
        userRepository.save(buyer);

    }

    @Override
    public void expireForecastById(Long id) {
        Forecast forecast = forecastRepository.findById(id).orElse(null);

        if (forecast != null) {
            forecast.setClosed(LocalDateTime.now());
            forecast.setActive(false);
            forecastRepository.save(forecast);
        }
    }

    @Override
    public void removeForecastById(Long id) {
        forecastRepository.deleteById(id);
    }

}
