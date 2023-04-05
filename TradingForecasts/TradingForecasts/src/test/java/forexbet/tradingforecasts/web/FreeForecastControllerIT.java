package forexbet.tradingforecasts.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class FreeForecastControllerIT {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void testGetAllActiveFreeForecasts() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/free-forecasts")
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(view().name("free-forecasts"));
//    }

    @Test
    @WithMockUser
    void testGetOrderByID_ThrowException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/free-forecasts/88"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("order-not-found"));
    }
}
