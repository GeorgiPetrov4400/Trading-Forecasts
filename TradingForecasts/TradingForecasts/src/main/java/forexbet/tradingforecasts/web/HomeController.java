package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ForecastService forecastService;
    private final UserService userService;

    public HomeController(ForecastService forecastService, UserService userService) {
        this.forecastService = forecastService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

//    @GetMapping("/orders/order")
//    public String order(Model model) {
//
////        List<ForecastDTO> currentAdminForecasts = userService.getCurrentAdminForecasts();
////        model.addAttribute("currentAdminForecasts", currentAdminForecasts);
//
//        List<ForecastDTO> allActiveForecast = forecastService.getActiveForecasts();
//        model.addAttribute("allActiveForecast", allActiveForecast);
//
//
//
////        List<Forecast> currentUserForecasts = userService.getCurrentUserForecasts();
////        model.addAttribute("currentUserForecasts", currentUserForecasts);
//
////        List<Forecast> ownForecastsAdded = this.forecastService.getOwnForecastsAdded(currentUser.getId());
////        model.addAttribute("ownForecastsAdded", ownForecastsAdded);
////
////        List<Forecast> allActiveForecasts = forecastService.getAllActiveForecasts(currentUser.getId());
////        model.addAttribute("allActiveForecasts", allActiveForecasts);
//
//
//
//        return "order";
//    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/free-forecasts")
    public String freeForecasts() {
        return "free-forecast";
    }
}
