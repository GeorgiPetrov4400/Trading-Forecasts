package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.entity.*;
import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.service.cloudinary.PictureCloudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForecastAddControllerIT {

    private PictureCloudService mockPictureCloudService;

    @Autowired
    private MockMvc mockMvc;

    private User testAdmin;

    private Forecast testForecast;

    @BeforeEach
    void setUp() {
        testAdmin = createTestAdmin("admin@example.com", "Admin");
//        testForecast = createTestForecast("test eur",
//                "https://res.cloudinary.com/dtg97g3ym/image/upload/v1680710447/e3ab41e6-2295-455b-a0c7-0419068ccc92.png",
//                BigDecimal.valueOf(1), "EurUsd", "Short", true);
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

    private Forecast createTestForecast
            (String description, MultipartFile imageFile, BigDecimal price, String category, String type, boolean isActive) {

        Category categoryEnum = new Category().setCategory(CategoryNameEnum.EurUsd);

        testForecast = new Forecast()
                .setDescription(description)
                .setPrice(price)
                .setCategory(categoryEnum)
                .setForecastType(ForecastTypeEnum.valueOf(type))
                .setActive(true);

        String pictureUrl = mockPictureCloudService.savePicture(imageFile);

        Picture testPicture = new Picture();
        testPicture.setForecast(testForecast);
        testPicture.setTitle(imageFile.getOriginalFilename());
        testPicture.setUrl(pictureUrl);
        testForecast.setPicturesUrl(Collections.singleton(testPicture));

        return testForecast;
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
                        .param("description", "test")
                        .param("pictureUrl", "1")
                        .param("price", "8.88")
                        .param("isActive", "false")
                        .param("category", "gold")
                        .param("type", "unknown")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("add"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "Admin")
    void testAddForecast_Success() throws Exception {
        mockMvc.perform(post("/forecasts/add")
                        .param("description", "test eur")
                        .param("pictureUrl", "https://res.cloudinary.com/dtg97g3ym/image/upload/v1680710447/e3ab41e6-2295-455b-a0c7-0419068ccc92.png")
                        .param("price", "1")
                        .param("isActive", "true")
                        .param("category", "EurUsd")
                        .param("type", "Short")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
