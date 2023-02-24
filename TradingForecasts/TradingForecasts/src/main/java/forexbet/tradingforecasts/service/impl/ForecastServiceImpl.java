package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.stereotype.Service;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;


    public ForecastServiceImpl(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }
}
