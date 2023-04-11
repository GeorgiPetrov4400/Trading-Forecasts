package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.UserRole;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIT {

    @Autowired
    private MockMvc mockMvc;

//    private String defaultAdminPass;

    private User testAdmin;

    @BeforeEach
    void setUp() {
        testAdmin = createTestAdmin("admin@example.com", "Admin");
    }

    private User createTestAdmin(String email, String username) {

        UserRole adminRole = new UserRole().setRole(UserRoleEnum.Admin);
        UserRole moderatorRole = new UserRole().setRole(UserRoleEnum.Moderator);
        UserRole userRole = new UserRole().setRole(UserRoleEnum.User);

        User testAdminUser = new User()
                .setEmail(email)
                .setUsername(username)
                .setPassword("12345")
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setRoles(List.of(adminRole, moderatorRole, userRole));

        return testAdminUser;
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testGetEurUsd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/eur-usd")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.investing.com/currencies/eur-usd-historical-data"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testGetEurGbp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/eur-gbp")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.investing.com/currencies/eur-gbp-historical-data"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testGetGold() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/gold")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.dailyfx.com/gold-price"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testGetDax() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/dax")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.fxcm.com/markets/quotes/indices/ger30/"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testGetDowJones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/dow-jones")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.fxcm.com/markets/quotes/indices/us30/"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testGetNasdaq() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/nasdaq")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:https://www.fxcm.com/markets/quotes/indices/nas100/"));
    }

}
