package forexbet.tradingforecasts.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forecasts")
public class ForecastController {

//    private final ForecastService forecastService;
//
//    public ForecastController(ForecastService forecastService) {
//        this.forecastService = forecastService;
//    }

//    @ModelAttribute
//    public ForecastAddDTO forecastAddDTO() {
//        return new ForecastAddDTO();
//    }

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

//    @GetMapping("/add")
//    public String add() {
//        return "forecast-add";
//    }
//
//    @PostMapping("/add")
//    public String addForecast(@Valid ForecastAddDTO forecastAddDTO,
//                              BindingResult bindingResult,
//                              RedirectAttributes redirectAttributes,
//                              Principal principal) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("forecastAddDTO", forecastAddDTO)
//                    .addFlashAttribute("org.springframework.validation.BindingResult.forecastAddDTO",
//                            bindingResult);
//
//            return "redirect:add";
//        }
//
//        forecastService.addForecast(principal, forecastAddDTO);
//
//        return "redirect:/";
//    }
}
