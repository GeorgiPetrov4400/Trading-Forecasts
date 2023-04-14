package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ChangeAccountRoleDTO;
import forexbet.tradingforecasts.model.dto.ChangeAccountUsernameDTO;
import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.view.UserViewModel;
import forexbet.tradingforecasts.service.UserService;
import forexbet.tradingforecasts.service.account.AccountService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ChangeAccountDetailsController {

    private final AccountService accountService;
    private final UserService userService;

    public ChangeAccountDetailsController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @ModelAttribute
    public ChangeAccountUsernameDTO changeAccountUsernameDTO() {
        return new ChangeAccountUsernameDTO();
    }

    @ModelAttribute
    public ChangeAccountRoleDTO changeAccountRoleDTO() {
        return new ChangeAccountRoleDTO();
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
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeAccountUsernameDTO", bindingResult);

            return "redirect:my-account";
        }

        try {
            accountService.editAccountUsername(changeAccountUsernameDTO, principal);
        } catch (Exception e) {
            ObjectError error = new ObjectError("globalError", e.getMessage());
            bindingResult.addError(error);

            redirectAttributes
                    .addFlashAttribute("changeAccountUsernameDTO", changeAccountUsernameDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.changeAccountUsernameDTO", bindingResult);

            return "redirect:my-account";
        }

        return "redirect:/";
    }

    @GetMapping("/change-role")
    public String getMyRoles(Model model, Principal principal) {

        UserViewModel adminUser = userService.getCurrentAdminAccount(principal.getName());

        model.addAttribute("account", adminUser);
        model.addAttribute("changeAccountRoleDTO", new ChangeAccountRoleDTO());

        return "change-role";
    }

    @PatchMapping("/change-role/{id}")
    public String changeUserRole(@PathVariable("id") Long id, ChangeAccountRoleDTO changeAccountRoleDTO) {

        userService.changeUserRole(id, changeAccountRoleDTO);

        return "redirect:/change-role";
    }
}
