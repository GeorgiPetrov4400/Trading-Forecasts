package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.repository.PictureRepository;
import forexbet.tradingforecasts.service.ForecastService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    private OrderController orderController;

    @Mock
    private ForecastService forecastService;

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(forecastService, pictureRepository);
    }

    @Test
    void testBuyForecast() {
        Long forecastId = 1L;
        String expectedViewName = "redirect:/orders/order";
        String viewName = orderController.buyForecast(forecastId, principal);
        verify(forecastService, times(1)).buyForecast(forecastId, principal);
        Assertions.assertEquals(expectedViewName, viewName);
    }

    @Test
    void testExpiredForecast() {
        Long forecastId = 1L;
        String expectedViewName = "redirect:/orders/order";
        String viewName = orderController.expiredForecast(forecastId);
        verify(forecastService, times(1)).expireForecastById(forecastId);
        Assertions.assertEquals(expectedViewName, viewName);
    }

    @Test
    void testRemoveForecast() {
        Long forecastId = 1L;
        String expectedViewName = "redirect:/orders/order";
        String viewName = orderController.removeForecast(forecastId);
        verify(forecastService, times(1)).removeForecastById(forecastId);
    }
}
