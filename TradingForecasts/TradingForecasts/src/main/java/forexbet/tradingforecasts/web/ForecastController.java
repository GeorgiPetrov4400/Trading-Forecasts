package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.service.ForecastService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/forecasts")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @ModelAttribute
    public ForecastAddDTO forecastAddDTO() {
        return new ForecastAddDTO();
    }

    @GetMapping("/eur-usd-forecast")
    public String forecastEurUsd() {
        return "eur-usd-forecast";
    }

    @GetMapping("/eur-gbp-forecast")
    public String forecastEurGbp() {
        return "eur-gbp-forecast";
    }

    @GetMapping("/gold-forecast")
    public String forecastGold() {
        return "gold-forecast";
    }

    @GetMapping("/dax-forecast")
    public String forecastDax() {
        return "dax-forecast";
    }

    @GetMapping("/dow-jones-forecast")
    public String forecastDowJones() {
        return "dow-jones-forecast";
    }

    @GetMapping("/nasdaq-forecast")
    public String forecastNasdaq() {
        return "nasdaq-forecast";
    }

    @GetMapping("/add")
    public String add() {
        return "forecast-add";
    }

    @PostMapping("/add")
    public String addForecast(@Valid ForecastAddDTO forecastAddDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Principal principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("forecastAddDTO", forecastAddDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.forecastAddDTO",
                            bindingResult);

            return "redirect:add";
        }

        forecastService.addForecast(principal, forecastAddDTO);

        return "redirect:/";

    }

//    @GetMapping("/order/buy/{id}")
//    public String buyForecast(@PathVariable Long id) {
////        this.forecastService.buyForecast(id, currentUser.getId());
//
//        return "redirect:/orders/order";
//    }
//
//    @GetMapping("/order/expire/{id}")
//    public String expiredForecast(@PathVariable Long id) {
//        forecastService.expireForecastById(id);
//
//        return "redirect:/orders/order";
//    }
}
