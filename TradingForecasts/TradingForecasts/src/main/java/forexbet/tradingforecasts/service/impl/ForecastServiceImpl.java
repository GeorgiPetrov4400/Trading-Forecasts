package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.Picture;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.UserRole;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.repository.PictureRepository;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.service.cloudinary.PictureCloudService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
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
    private final PictureRepository pictureRepository;
    private final PictureCloudService pictureCloudService;


    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, UserService userService, CategoryService categoryService, UserRepository userRepository, PictureRepository pictureRepository, PictureCloudService pictureCloudService) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.pictureCloudService = pictureCloudService;
    }

    @Override
    public void createForecast(ForecastAddDTO forecastAddDTO, Principal principal, MultipartFile imageFile) {
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

        if (adminOptional.isPresent()) {
            Forecast forecast = modelMapper.map(forecastAddDTO, Forecast.class);
            forecast.setAdmin(userService.findById(adminOptional.get().getId()))
                    .setCategory(categoryService.findByCategoryNameEnum(forecastAddDTO.getCategory()))
                    .setForecastType(forecastAddDTO.getType())
                    .setDescription(forecastAddDTO.getDescription())
                    .setPrice(forecastAddDTO.getPrice())
                    .setCreated(LocalDateTime.now())
                    .setActive(true);

            String pictureUrl = pictureCloudService.savePicture(imageFile);

            Picture picture = new Picture();
            picture.setForecast(forecast);
            picture.setTitle(imageFile.getOriginalFilename());
            picture.setUrl(pictureUrl);

            forecast.setPicturesUrl(Collections.singleton(picture));
            pictureRepository.save(picture);
            forecastRepository.save(forecast);
        }
    }

    @Override
    public List<ForecastDTO> getUserBoughtForecasts(Principal principal) {
        Optional<User> buyerOptional = userRepository.findByUsername(principal.getName());

        return buyerOptional.map(user -> forecastRepository.findAllByBuyer_IdAndPriceNotNullOrderByIdDesc(user.getId())
                .stream().map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
                .collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<ForecastDTO> getOwnForecastsAdded(Principal principal) {
        Optional<User> adminOptional = userRepository.findByUsername(principal.getName());

        return adminOptional.map(user -> forecastRepository.findByAdmin_IdAndClosedIsNull(user.getId()).stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class))
                .collect(Collectors.toList())).orElse(null);
    }

    @Override
    public List<ForecastDTO> getActiveForecasts() {
        return forecastRepository.findAllByClosedIsNullAndPriceIsNotNullOrderByCreatedDesc().stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<ForecastDTO> getExpiredForecasts() {
        return forecastRepository.findAllByClosedIsNotNullOrderByClosedDesc().stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void buyForecast(Long id, Principal principal) {
        Forecast forecast = forecastRepository.findById(id).orElse(null);

        Optional<User> buyerOptional = userRepository.findByUsername(principal.getName());

        if (buyerOptional.isPresent()) {
            User buyer = userRepository.findById(buyerOptional.get().getId()).orElse(null);

            if (buyer != null && buyer.getRoles().size() == 1 && forecast != null && forecast.getPrice() != null) {
                if (buyer.getForecasts().contains(forecast)) {
                    return;
                }
                buyer.getForecasts().add(forecast);
                forecast.getBuyer().add(buyer);
                forecastRepository.saveAndFlush(forecast);
                userRepository.save(buyer);
            }
        }
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

    @Override
    public List<ForecastDTO> getAllActiveFreeForecasts() {
        List<ForecastDTO> freeForecasts = forecastRepository.findAllByClosedIsNullAndPriceIsNull().stream()
                .map(forecast -> modelMapper.map(forecast, ForecastDTO.class)).toList();

        for (ForecastDTO freeForecast : freeForecasts) {
            Optional<Picture> byForecastId = pictureRepository.findByForecastId(freeForecast.getId());
            Picture picture = byForecastId.get();
            freeForecast.setPictureUrl(picture.getUrl());
        }

        return freeForecasts;
    }

}
