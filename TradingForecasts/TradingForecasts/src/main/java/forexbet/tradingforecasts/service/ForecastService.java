package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.service.ForecastServiceModel;

import java.util.List;

public interface ForecastService {

    void addForecast(ForecastServiceModel forecastServiceModel);

    List<Forecast> getAllActiveForecasts(long id);
}
