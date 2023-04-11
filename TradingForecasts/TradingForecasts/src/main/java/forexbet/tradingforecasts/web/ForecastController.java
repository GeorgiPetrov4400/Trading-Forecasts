package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastDTO;
import forexbet.tradingforecasts.model.dto.PictureDTO;
import forexbet.tradingforecasts.model.entity.Category;
import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import forexbet.tradingforecasts.service.CategoryService;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/forecasts")
public class ForecastController {

    private final ForecastService forecastService;
    private final PictureService pictureService;
    private final CategoryService categoryService;

    public ForecastController(ForecastService forecastService, PictureService pictureService, CategoryService categoryService) {
        this.forecastService = forecastService;
        this.pictureService = pictureService;
        this.categoryService = categoryService;
    }

    @GetMapping("/ui")
    public String freeForecasts() {
        return "free-forecasts";
    }

//    @GetMapping("/ui/all")
//    public String getOrders() {
//        throw new NullPointerException("Server Error");
//    }

    @GetMapping("/eur-usd-forecast")
    public String forecastsEurUsd(Model model) {
        Category byCategoryNameEnum = categoryService.findByCategoryNameEnum(CategoryNameEnum.EurUsd);

        List<ForecastDTO> allActiveEurUsdForecasts =
                forecastService.getActiveForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allActiveEurUsdForecasts);
        model.addAttribute("allActiveEurUsdForecasts", allActiveEurUsdForecasts);

        List<ForecastDTO> allExpiredEurUsdForecast =
                forecastService.getAllExpiredForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allExpiredEurUsdForecast);
        model.addAttribute("allExpiredEurUsdForecast", allExpiredEurUsdForecast);

        return "eur-usd-forecast";
    }

    @GetMapping("/eur-gbp-forecast")
    public String forecastEurGbp(Model model) {
        Category byCategoryNameEnum = categoryService.findByCategoryNameEnum(CategoryNameEnum.EurGbp);

        List<ForecastDTO> allActiveEurGbpForecasts =
                forecastService.getActiveForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allActiveEurGbpForecasts);
        model.addAttribute("allActiveEurGbpForecasts", allActiveEurGbpForecasts);

        List<ForecastDTO> allExpiredEurGbpForecast =
                forecastService.getAllExpiredForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allExpiredEurGbpForecast);
        model.addAttribute("allExpiredEurGbpForecast", allExpiredEurGbpForecast);

        return "eur-gbp-forecast";
    }

    @GetMapping("/gold-forecast")
    public String forecastGold(Model model) {
        Category byCategoryNameEnum = categoryService.findByCategoryNameEnum(CategoryNameEnum.Gold);

        List<ForecastDTO> allActiveGoldForecasts =
                forecastService.getActiveForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allActiveGoldForecasts);
        model.addAttribute("allActiveGoldForecasts", allActiveGoldForecasts);

        List<ForecastDTO> allExpiredGoldForecast =
                forecastService.getAllExpiredForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allExpiredGoldForecast);
        model.addAttribute("allExpiredGoldForecast", allExpiredGoldForecast);

        return "gold-forecast";
    }

    @GetMapping("/dax-forecast")
    public String forecastDax(Model model) {
        Category byCategoryNameEnum = categoryService.findByCategoryNameEnum(CategoryNameEnum.Dax);

        List<ForecastDTO> allActiveDaxForecasts =
                forecastService.getActiveForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allActiveDaxForecasts);
        model.addAttribute("allActiveDaxForecasts", allActiveDaxForecasts);

        List<ForecastDTO> allExpiredDaxForecast =
                forecastService.getAllExpiredForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allExpiredDaxForecast);
        model.addAttribute("allExpiredDaxForecast", allExpiredDaxForecast);

        return "dax-forecast";
    }

    @GetMapping("/dow-jones-forecast")
    public String forecastDowJones(Model model) {
        Category byCategoryNameEnum = categoryService.findByCategoryNameEnum(CategoryNameEnum.DowJones);

        List<ForecastDTO> allActiveDowJonesForecasts =
                forecastService.getActiveForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allActiveDowJonesForecasts);
        model.addAttribute("allActiveDowJonesForecasts", allActiveDowJonesForecasts);

        List<ForecastDTO> allExpiredDowJonesForecast =
                forecastService.getAllExpiredForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allExpiredDowJonesForecast);
        model.addAttribute("allExpiredDowJonesForecast", allExpiredDowJonesForecast);

        return "dow-jones-forecast";
    }

    @GetMapping("/nasdaq-forecast")
    public String forecastNasdaq(Model model) {
        Category byCategoryNameEnum = categoryService.findByCategoryNameEnum(CategoryNameEnum.Nasdaq);

        List<ForecastDTO> allActiveNasdaqForecasts =
                forecastService.getActiveForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allActiveNasdaqForecasts);
        model.addAttribute("allActiveNasdaqForecasts", allActiveNasdaqForecasts);

        List<ForecastDTO> allExpiredNasdaqForecast =
                forecastService.getAllExpiredForecastsByCategory(byCategoryNameEnum);

        addForecastPicture(allExpiredNasdaqForecast);
        model.addAttribute("allExpiredNasdaqForecast", allExpiredNasdaqForecast);

        return "nasdaq-forecast";
    }

    private void addForecastPicture(List<ForecastDTO> allActiveForecasts) {
        for (ForecastDTO activeForecast : allActiveForecasts) {
            PictureDTO forecastId = pictureService.findByForecastId(activeForecast.getId());
            activeForecast.setPictureUrl(forecastId.getUrl());
        }
    }


}
