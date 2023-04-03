package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.entity.Picture;
import forexbet.tradingforecasts.repository.PictureRepository;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final ForecastService forecastService;
    private final PictureRepository pictureRepository;

    public OrderController(ForecastService forecastService, PictureRepository pictureRepository) {
        this.forecastService = forecastService;
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/order")
    public String getAllActiveForecast(Principal principal, Model model) {

        List<ForecastDTO> userBoughtForecasts = forecastService.getUserBoughtForecasts(principal);
        model.addAttribute("userBoughtForecasts", userBoughtForecasts);

        List<ForecastDTO> ownForecastsAdded = forecastService.getOwnForecastsAdded(principal);
        model.addAttribute("ownForecastsAdded", ownForecastsAdded);

        List<ForecastDTO> allActiveForecasts = forecastService.getActiveForecasts();
        List<Picture> pictures = new ArrayList<>();

        for (ForecastDTO activeForecast : allActiveForecasts) {
            Optional<Picture> byForecastId = pictureRepository.findByForecastId(activeForecast.getId());
            Picture picture = byForecastId.get();
            pictures.add(picture);
        }

        model.addAttribute("allPictures", pictures);
        model.addAttribute("allActiveForecast", allActiveForecasts);

        List<ForecastDTO> expiredForecasts = forecastService.getExpiredForecasts();
        model.addAttribute("expiredForecasts", expiredForecasts);

        return "order";
    }

    @GetMapping("/order/buy/{id}")
    public String buyForecast(@PathVariable Long id, Principal principal) {
        forecastService.buyForecast(id, principal);

        return "redirect:/orders/order";
    }

    @GetMapping("/order/expire/{id}")
    public String expiredForecast(@PathVariable Long id) {
        forecastService.expireForecastById(id);

        return "redirect:/orders/order";
    }

    @GetMapping("/orders/remove/{id}")
    public String removeForecast(@PathVariable Long id) {
        forecastService.removeForecastById(id);

        return "redirect:/orders/order";
    }
}
