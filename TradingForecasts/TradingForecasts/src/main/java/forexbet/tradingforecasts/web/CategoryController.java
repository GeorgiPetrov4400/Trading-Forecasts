package forexbet.tradingforecasts.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping("/eur-usd")
    public String categoryEurUsd() {
        return "eur-usd";
    }

    @GetMapping("/eur-gbp")
    public String categoryEurGbp() {
        return "eur-gbp";
    }

    @GetMapping("/gold")
    public String categoryGold() {
        return "gold";
    }

    @GetMapping("/dax")
    public String categoryDax() {
        return "dax";
    }

    @GetMapping("/dow-jones")
    public String categoryDowJones() {
        return "dow-jones";
    }

    @GetMapping("/nasdaq")
    public String categoryNasdaq() {
        return "nasdaq";
    }
}
