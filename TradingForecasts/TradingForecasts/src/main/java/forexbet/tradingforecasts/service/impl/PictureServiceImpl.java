package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.repository.PictureRepository;
import forexbet.tradingforecasts.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }
}
