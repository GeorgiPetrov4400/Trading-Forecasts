package forexbet.tradingforecasts.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetRegister() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register")
                        .with(csrf())).andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("email", "gosho@example.com")
                        .param("username", "Gosho")
                        .param("password", "12345")
                        .param("confirmPassword", "12345")
                        .param("firstName", "Gosho")
                        .param("lastName", "Goshev")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));
    }


}
