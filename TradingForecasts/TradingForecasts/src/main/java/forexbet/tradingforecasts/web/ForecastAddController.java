package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/forecasts")
public class ForecastAddController {

    private final ForecastService forecastService;


    public ForecastAddController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @ModelAttribute
    public ForecastAddDTO forecastAddDTO() {
        return new ForecastAddDTO();
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

//        forecastService.createForecast(principal, forecastAddDTO);
        forecastService.createForecast(forecastAddDTO, principal, forecastAddDTO.getPictureUrl());

        return "redirect:/";

    }
}
