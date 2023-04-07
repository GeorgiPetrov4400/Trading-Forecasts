package forexbet.tradingforecasts.util;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.service.ForecastService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteExpiredForecastSchedulerTest {

    @Test
    public void testDeleteExpiredForecasts() {
        ForecastService forecastService = mock(ForecastService.class);

        List<ForecastDTO> expiredForecasts = new ArrayList<>();
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        LocalDateTime dateExpired = currentDateAndTime.minusMonths(3);
        ForecastDTO forecast1 = new ForecastDTO();
        forecast1.setId(1L);
        forecast1.setClosed(dateExpired);
        expiredForecasts.add(forecast1);
        ForecastDTO forecast2 = new ForecastDTO();
        forecast2.setId(2L);
        forecast2.setClosed(dateExpired);
        expiredForecasts.add(forecast2);

        when(forecastService.getExpiredForecasts()).thenReturn(expiredForecasts);

        DeleteExpiredForecastScheduler scheduler = new DeleteExpiredForecastScheduler(forecastService);

        scheduler.deleteForecastsExpireDateIsMoreThanTwoMonths();

        verify(forecastService, times(1)).removeForecastById(1L);
        verify(forecastService, times(1)).removeForecastById(2L);
    }
}
