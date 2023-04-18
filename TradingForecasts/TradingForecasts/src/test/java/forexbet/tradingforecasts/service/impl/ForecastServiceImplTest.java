package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.Picture;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.repository.PictureRepository;
import forexbet.tradingforecasts.repository.UserRepository;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.service.cloudinary.PictureCloudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForecastServiceImplTest {

    @Mock
    private ForecastRepository forecastRepository;
    @Mock
    private UserService userService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PictureRepository pictureRepository;
    @Mock
    private PictureCloudService pictureCloudService;

    private ModelMapper modelMapper = new ModelMapper();

    private ForecastServiceImpl forecastService;

    @BeforeEach
    void setUp() throws Exception {
        forecastService = new ForecastServiceImpl(forecastRepository, modelMapper, userService, categoryService,
                userRepository, pictureRepository, pictureCloudService);
    }

    @Test
    public void createForecast_withValidData_shouldSaveForecast() throws Exception {
        // arrange
        ForecastAddDTO forecastAddDTO = new ForecastAddDTO();
        forecastAddDTO.setCategory(CategoryNameEnum.EurUsd);
        forecastAddDTO.setType(ForecastTypeEnum.Short);
        forecastAddDTO.setDescription("DESCRIPTION");
        forecastAddDTO.setPrice(BigDecimal.valueOf(1.0));

        User admin = new User();
        admin.setId(1L);
        admin.setUsername("admin");

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("admin");


        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(userService.findById(1L)).thenReturn(admin);
        when(categoryService.findByCategoryNameEnum(any())).thenReturn(null);
        when(pictureCloudService.savePicture(any())).thenReturn("URL");

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        // act
        forecastService.createForecast(forecastAddDTO, principal, file);

        // assert
        verify(forecastRepository).save(any(Forecast.class));
        verify(pictureRepository).save(any(Picture.class));
    }

    @Test
    @WithMockUser
    public void createForecast_withInvalidAdmin_shouldNotSaveForecast() throws Exception {
        // arrange
        ForecastAddDTO forecastAddDTO = new ForecastAddDTO();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("nonexistent_admin");

        // act
        forecastService.createForecast(forecastAddDTO, principal, null);

        // assert
        verify(forecastRepository, never()).save(any(Forecast.class));
    }

//    @Test
//    @WithMockUser(username = username, roles = "USER")
//    void testGetUserBoughtForecasts() {
//        List<ForecastViewModel> expectedForecasts = new ArrayList<>();
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
//        when(forecastRepository.findAllByBuyer_IdAndPriceNotNullOrderByIdDesc(1L)).thenReturn(Collections.singletonList(new Forecast()));
//        when(modelMapper.map(new Forecast(), ForecastViewModel.class)).thenReturn(new ForecastViewModel());
//
////        List<ForecastViewModel> actualForecasts = forecastService.getUserBoughtForecasts(username);
//
////        assertThat(actualForecasts).isEqualTo(expectedForecasts);
//    }
//
//    @Test
//    @WithMockUser(username = username, roles = "ADMIN")
//    void testGetOwnForecastsAdded() {
//        List<ForecastViewModel> expectedForecasts = new ArrayList<>();
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
//        when(forecastRepository.findByAdmin_IdAndClosedIsNullOrderByCreatedDesc(1L)).thenReturn(Collections.singletonList(new Forecast()));
//        when(modelMapper.map(new Forecast(), ForecastViewModel.class)).thenReturn(new ForecastViewModel());
//
////        List<ForecastViewModel> actualForecasts = forecastService.getOwnForecastsAdded(username);
//
////        assertThat(actualForecasts).isEqualTo(expectedForecasts);
//    }

//    @Test
//    void testGetActiveForecasts() {
//        List<ForecastViewModel> expectedForecasts = new ArrayList<>();
//        when(forecastRepository.findAllByClosedIsNullAndPriceIsNotNullOrderByCreatedDesc()).thenReturn(Collections.singletonList(new Forecast()));
//        when(modelMapper.map(new Forecast(), ForecastViewModel.class)).thenReturn(new ForecastViewModel());
//
//        List<ForecastViewModel> actualForecasts = forecastService.getActiveForecasts();
//
//        assertThat(actualForecasts).isEqualTo(expectedForecasts);
//    }
}