package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.entity.User;
import forexbet.tradingforecasts.model.entity.UserRole;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForecastAddControllerIT {

    @Autowired
    private MockMvc mockMvc;

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
    void testGetAddForecast() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/forecasts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("forecast-add"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testAddForecast_Failed() throws Exception {
        mockMvc.perform(post("/forecasts/add")
                        .param("description", "test1")
                        .param("pictureUrl", "test.jpg")
                        .param("price", "8.88")
                        .param("isActive", "true")
                        .param("category", "gold")
                        .param("type", "short")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("add"));
    }
}
