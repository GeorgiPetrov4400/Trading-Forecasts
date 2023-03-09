package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.entity.Forecast;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.util.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CurrentUser currentUser;
    private final ForecastService forecastService;
    private final UserService userService;

    public HomeController(CurrentUser currentUser, ForecastService forecastService, UserService userService) {
        this.currentUser = currentUser;
        this.forecastService = forecastService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String index() {
//        if (currentUser.getId() == null) {
//            return "redirect:/";
//        }

        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/orders/order")
    public String order(Model model) {
        if (currentUser.getId() == null) {
            return "redirect:/users/login";
        }

        List<Forecast> currentUserForecasts = userService.getCurrentUserForecasts();
        model.addAttribute("currentUserForecasts", currentUserForecasts);

        List<Forecast> ownForecastsAdded = this.forecastService.getOwnForecastsAdded(currentUser.getId());
        model.addAttribute("ownForecastsAdded", ownForecastsAdded);

        List<Forecast> allActiveForecasts = forecastService.getAllActiveForecasts(currentUser.getId());
        model.addAttribute("allActiveForecasts", allActiveForecasts);

        return "order";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
