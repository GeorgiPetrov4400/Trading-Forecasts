package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.dto.PictureDTO;

public interface PictureService {

    PictureDTO findByForecastId(Long id);
}
