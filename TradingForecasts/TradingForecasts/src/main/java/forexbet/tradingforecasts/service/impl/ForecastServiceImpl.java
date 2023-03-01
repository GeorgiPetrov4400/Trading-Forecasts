package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;

    public ForecastServiceImpl(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

}
