package forexbet.tradingforecasts.service.account;

import forexbet.tradingforecasts.model.dto.ChangeAccountUsernameDTO;
import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public void editAccountUsername(ChangeAccountUsernameDTO changeAccountUsernameDTO,
                                    UserDetails principal) {

        User byPrincipal = userService.getUserByPrincipal(principal.getUsername());

        if (byPrincipal != null) {
            User changeUsername = changeUserUsername(changeAccountUsernameDTO, byPrincipal);
            userService.saveUserChanges(changeUsername);
        }
    }

    private User changeUserUsername(ChangeAccountUsernameDTO changeAccountUsernameDTO, User user) {
        User byUsername = userService.getUserByUsername(changeAccountUsernameDTO.getUsername());
        if (byUsername != null) {
            throw new RuntimeException("Username is already in use");
        }

        return user.setUsername(changeAccountUsernameDTO.getUsername());
    }
}
