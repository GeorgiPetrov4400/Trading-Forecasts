package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastService forecastService;

    public ForecastServiceImpl(@Lazy ForecastService forecastService) {
        this.forecastService = forecastService;
    }
}
