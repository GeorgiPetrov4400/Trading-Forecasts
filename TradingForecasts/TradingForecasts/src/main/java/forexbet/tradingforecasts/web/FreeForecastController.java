package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FreeForecastController {

    private final ForecastService forecastService;

    public FreeForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/api/free-forecasts")
    public ResponseEntity<List<ForecastDTO>> getAllActiveFreeForecasts() {
        return ResponseEntity.ok(forecastService.getAllActiveFreeForecasts());
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
