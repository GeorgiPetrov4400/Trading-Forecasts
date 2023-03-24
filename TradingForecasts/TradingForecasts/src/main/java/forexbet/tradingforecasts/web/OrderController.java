package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.exception.OrderNotFoundException;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    private final ForecastService forecastService;

    public OrderController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

//    @GetMapping("/orders/order")
//    public String orders() {
//        return "order";
//    }

    @GetMapping("/orders/order")
    public String getAllActiveForecast(Principal principal, Model model) {

        List<ForecastDTO> userBoughtForecasts = forecastService.getUserBoughtForecasts(principal);
        model.addAttribute("userBoughtForecasts", userBoughtForecasts);

        List<ForecastDTO> ownForecastsAdded = forecastService.getOwnForecastsAdded(principal);
        model.addAttribute("ownForecastsAdded", ownForecastsAdded);

        List<ForecastDTO> allActiveForecasts = forecastService.getActiveForecasts();
        model.addAttribute("allActiveForecast", allActiveForecasts);

        return "order";
    }

    @GetMapping("/orders/{id}")
    public String getOrderById(@PathVariable("id") Long id) {
        throw new OrderNotFoundException(id);
    }

    @GetMapping("/orders")
    public String getOrders() {
        throw new NullPointerException("Server Error");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView onOrderNotFound(OrderNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("order-not-found");

        modelAndView.addObject("orderId", onfe.getOrderId());

        return modelAndView;
    }
}
