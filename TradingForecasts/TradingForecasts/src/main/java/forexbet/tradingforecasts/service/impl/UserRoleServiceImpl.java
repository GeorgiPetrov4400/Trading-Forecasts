package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.repository.UserRoleRepository;
import forexbet.tradingforecasts.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
}
