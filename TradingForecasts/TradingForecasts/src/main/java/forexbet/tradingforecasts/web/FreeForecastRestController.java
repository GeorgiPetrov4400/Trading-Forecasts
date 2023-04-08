package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.exception.OrderNotFoundException;
import forexbet.tradingforecasts.service.ForecastService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/free-forecasts")
public class FreeForecastRestController {

    private final ForecastService forecastService;

    public FreeForecastRestController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ForecastDTO>> getAllActiveFreeForecasts() {
        return ResponseEntity.ok(forecastService.getAllActiveFreeForecasts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForecastDTO> getOrderById(@PathVariable("id") Long id) {
        throw new OrderNotFoundException(id);
    }
}
