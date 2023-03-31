package forexbet.tradingforecasts.util;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DeleteExpiredForecastScheduler {

    private final ForecastService forecastService;

    public DeleteExpiredForecastScheduler(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @Scheduled(cron = "0 2 0 * * *")
    public void deleteForecastsExpireDateIsMoreThanTwoMonths() {
        List<ForecastDTO> expiredForecasts = forecastService.getExpiredForecasts();

        for (ForecastDTO expiredForecast : expiredForecasts) {
            LocalDateTime currentDateAndTime = LocalDateTime.now();
            //      LocalDateTime currentDateAndTimeMinus3Months = currentDateAndTime.minusMonths(2);

            LocalDateTime dateExpired = expiredForecast.getClosed();
            //      LocalDateTime dateExpiredMinus3Months = dateExpired.minusMonths(2);

            long months = ChronoUnit.MONTHS.between(dateExpired, currentDateAndTime);
            if (months > 2) {
                forecastService.removeForecastById(expiredForecast.getId());
            }
        }
    }

//    @Scheduled(cron = "* */2 * * * *")
//    public void deleteForecastsExpiredBeforeMinute() {
//        List<ForecastDTO> expiredForecasts = forecastService.getExpiredForecasts();
//
//        for (ForecastDTO expiredForecast : expiredForecasts) {
//            LocalDateTime currentDateAndTime = LocalDateTime.now();
//            //      LocalDateTime currentDateAndTimeMinus3Months = currentDateAndTime.minusMonths(3);
//
//            LocalDateTime dateExpired = expiredForecast.getClosed();
//            //      LocalDateTime dateExpiredMinus3Months = dateExpired.minusMonths(3);
//
//            long minutes = ChronoUnit.MINUTES.between(dateExpired, currentDateAndTime);
//            if (minutes > 10) {
//                forecastService.removeForecastById(expiredForecast.getId());
//            }
//        }
//    }
}
