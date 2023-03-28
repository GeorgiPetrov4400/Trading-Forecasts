package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final ForecastService forecastService;

    public OrderController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

//    @GetMapping("/orders/order")
//    public String orders() {
//        return "order";
//    }

    @GetMapping("/order")
    public String getAllActiveForecast(Principal principal, Model model) {

        List<ForecastDTO> userBoughtForecasts = forecastService.getUserBoughtForecasts(principal);
        model.addAttribute("userBoughtForecasts", userBoughtForecasts);

        List<ForecastDTO> ownForecastsAdded = forecastService.getOwnForecastsAdded(principal);
        model.addAttribute("ownForecastsAdded", ownForecastsAdded);

        List<ForecastDTO> allActiveForecasts = forecastService.getActiveForecasts();
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

//    @GetMapping("/orders/order/{id}")
//    public String getOrderById(@PathVariable("id") Long id) {
//        throw new OrderNotFoundException(id);
//    }
//
//    @GetMapping()
//    public String getOrders() {
//        throw new NullPointerException("Server Error");
//    }
//
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    @ExceptionHandler(OrderNotFoundException.class)
//    public ModelAndView onOrderNotFound(OrderNotFoundException onfe) {
//        ModelAndView modelAndView = new ModelAndView("order-not-found");
//
//        modelAndView.addObject("orderId", onfe.getOrderId());
//
//        return modelAndView;
//    }
}
