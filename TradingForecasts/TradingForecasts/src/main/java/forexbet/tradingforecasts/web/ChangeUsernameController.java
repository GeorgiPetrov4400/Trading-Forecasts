package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ChangeAccountUsernameDTO;
import forexbet.tradingforecasts.service.account.AccountService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChangeUsernameController {

    private final AccountService accountService;

    public ChangeUsernameController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/my-account")
    public String getMyAccount() {
        return "my-account";
    }

    @PatchMapping("/my-account/edit/username")
    public String myAccountChangeUsername(@Valid ChangeAccountUsernameDTO changeAccountUsernameDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes,
                                          @AuthenticationPrincipal UserDetails principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeAccountUsername", changeAccountUsernameDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeAccountUsername", bindingResult);

            return "redirect:my-account";
        }

        accountService.editAccountUsername(changeAccountUsernameDTO, principal);

        redirectAttributes.addFlashAttribute("successChange", "Username change");

        return "redirect:/";
    }
}
