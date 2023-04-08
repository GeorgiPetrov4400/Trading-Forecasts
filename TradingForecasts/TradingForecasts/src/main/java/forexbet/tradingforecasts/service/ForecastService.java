package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.dto.ForecastDTO;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface ForecastService {

    void createForecast(ForecastAddDTO forecastAddDTO,Principal principal, MultipartFile imageFile);

    List<ForecastDTO> getOwnForecastsAdded(Principal principal);

    List<ForecastDTO> getActiveForecasts();

    List<ForecastDTO> getUserBoughtForecasts(Principal principal);

    void buyForecast(Long id, Principal principal);

    void expireForecastById(Long id);

    List<ForecastDTO> getExpiredForecasts();

    void removeForecastById(Long id);

    List<ForecastDTO> getAllActiveFreeForecasts();
}
