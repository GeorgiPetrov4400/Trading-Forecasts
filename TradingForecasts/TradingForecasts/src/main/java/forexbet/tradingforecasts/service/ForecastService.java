package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.entity.Forecast;

import java.security.Principal;
import java.util.List;

public interface ForecastService {

    void addForecast(Principal principal, ForecastAddDTO forecastAddDTO);

    List<Forecast> getAllActiveForecasts(long id);

    void buyForecast(Long id, Long currentUserId);

    void expireForecastById(Long id);

    List<Forecast> getOwnForecastsAdded(long id);

    List<ForecastDTO> getActiveForecasts();

//    List<ForecastDTO> getAllForecast();
}
