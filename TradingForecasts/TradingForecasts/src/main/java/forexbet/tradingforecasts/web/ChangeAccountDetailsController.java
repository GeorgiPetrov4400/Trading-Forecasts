package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ChangeAccountRoleDTO;
import forexbet.tradingforecasts.model.dto.ChangeAccountUsernameDTO;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.service.account.AccountService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChangeAccountDetailsController {

    private final AccountService accountService;
    private final UserService userService;

    public ChangeAccountDetailsController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/my-account")
    public String getMyAccount() {
        return "change-username";
    }

    @PatchMapping("/my-account")
    public String myAccountChangeUsername(@Valid ChangeAccountUsernameDTO changeAccountUsernameDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes,
                                          @AuthenticationPrincipal UserDetails principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeAccountUsernameDTO", changeAccountUsernameDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeAccountUsername", bindingResult);

            return "redirect:my-account";
        }

        accountService.editAccountUsername(changeAccountUsernameDTO, principal);

        redirectAttributes.addFlashAttribute("successChange", "Username change");

        return "redirect:/";
    }

    @GetMapping("/change-role")
    public String getMyRoles() {
        return "change-role";
    }

    @PatchMapping("/change-role/{id}")
    public String changeUserRole(@PathVariable("id") Long id, ChangeAccountRoleDTO changeAccountRoleDTO) {

        userService.changeUserRole(id, changeAccountRoleDTO);

        return "redirect:/change-role";
    }
}
