package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.dto.ForecastAddDTO;
import forexbet.tradingforecasts.model.entity.*;
import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;
import forexbet.tradingforecasts.model.entity.enums.UserRoleEnum;
import forexbet.tradingforecasts.repository.ForecastRepository;
import forexbet.tradingforecasts.repository.PictureRepository;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForecastAddControllerIT {

    private PictureCloudService mockPictureCloudService;

    private PictureRepository mockPictureRepository;

    private ForecastRepository mockForecastRepository;

    @Autowired
    private MockMvc mockMvc;

    private User testAdmin;

    private Forecast testForecast;

    private ForecastAddDTO testForecastAddDTO;

    @BeforeEach
    void setUp() {
        testAdmin = createTestAdmin("admin@example.com", "Admin");
        testForecast = createTestForecast(testForecastAddDTO, testAdmin, testForecastAddDTO.getPictureUrl());
//        testForecastAddDTO = createForecastAddDTO("test eur",
//                "https://res.cloudinary.com/dtg97g3ym/image/upload/v1680710447/e3ab41e6-2295-455b-a0c7-0419068ccc92.png",
//                BigDecimal.ONE, true, CategoryNameEnum.EurUsd, ForecastTypeEnum.Short);
    }

    private User createTestAdmin(String email, String username) {

        UserRole adminRole = new UserRole().setRole(UserRoleEnum.Admin);
        UserRole moderatorRole = new UserRole().setRole(UserRoleEnum.Moderator);
        UserRole userRole = new UserRole().setRole(UserRoleEnum.User);

        List<UserRole> allRoles = Arrays.asList(adminRole, moderatorRole, userRole);

        User testAdminUser = new User()
                .setEmail(email)
                .setUsername(username)
                .setPassword("12345")
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setRoles(allRoles);

        return testAdminUser;
    }

    private Forecast createTestForecast(ForecastAddDTO testForecastAddDTO, User testAdmin, MultipartFile imageFile) {

        User testAdminUser = createTestAdmin(testAdmin.getEmail(), testAdmin.getUsername());

        Category categoryEnum = new Category().setCategory(CategoryNameEnum.EurUsd);

        testForecast = new Forecast()
                .setAdmin(testAdminUser)
                .setCategory(categoryEnum)
                .setForecastType(testForecastAddDTO.getType())
                .setDescription(testForecastAddDTO.getDescription())
                .setPrice(testForecastAddDTO.getPrice())
                .setCreated(LocalDateTime.now())
                .setActive(true);

        String pictureUrl = mockPictureCloudService.savePicture(imageFile);

        Picture testPicture = new Picture();
        testPicture.setForecast(testForecast);
        testPicture.setTitle(imageFile.getOriginalFilename());
        testPicture.setUrl(pictureUrl);

        testForecast.setPicturesUrl(Collections.singleton(testPicture));
        mockPictureRepository.save(testPicture);
        return mockForecastRepository.save(testForecast);
    }

    private ForecastAddDTO createForecastAddDTO(String description, MultipartFile pictureUrl, BigDecimal price,
                                                boolean isActive, CategoryNameEnum category, ForecastTypeEnum type) {

        testForecastAddDTO.setDescription(description)
                .setPictureUrl(pictureUrl)
                .setPrice(price)
                .setActive(true)
                .setCategory(CategoryNameEnum.EurUsd)
                .setType(ForecastTypeEnum.Short);

        return testForecastAddDTO;
    }

//File imageFile = new File("src/test/resources", "testImgOne.png");
//
//        MockMultipartFile imageMultipartFile
//                = new MockMultipartFile(
//                        "fileData",
//                        imageFile.getName(),
//                        "image/png",
//                        Files.readAllBytes(imageFile.toPath()));
//
//        mockMvc.perform(multipart("/api/photos/temp/upload")
//                      .part(new MockPart("fileData",
//                                         imageFile.getName(),
//                                         imageMultipartFile.getBytes()))
//                      .contentType(
//                              "multipart/form-data; boundary=----WebKitFormBoundaryk1fn4D7ebCjmFPHl")
//                      .param("description", "testDescription")
//                      .param("tempRecipeId",
//                             recipeBM.getTempRecipeId().toString())
//                      .with(csrf())
//                     .andExpect(status().is2xxSuccessful())



//    @Test
//    @WithMockUser(username = "Admin", roles = "Admin")
//    void testGetAddForecast() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/forecasts/add"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("forecast-add"));
//    }
//
//    @Test
//    @WithMockUser(username = "Admin", roles = "Admin")
//    void testAddForecast_Failed() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/forecasts/add")
//                        .param("description", "t")
//                        .param("pictureUrl", "1")
//                        .param("price", "8.88")
//                        .param("isActive", "false")
//                        .param("category", "gold")
//                        .param("type", "unknown")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("add"));
//    }
//
//    @Test
//    @WithMockUser(username = "Admin", roles = "Admin")
//    void testAddForecast_Success() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/forecasts/add")
//                        .param("category", "EurUsd")
//                        .param("type", "Short")
//                        .param("description", "test eur")
//                        .param("price", "1")
////                        .param("created", "2023-04-07 14:20:31.125970")
//                        .param("isActive", "true")
//                        .param("pictureUrl", "https://res.cloudinary.com/dtg97g3ym/image/upload/v1680710447/e3ab41e6-2295-455b-a0c7-0419068ccc92.png")
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/orders/order"));
//    }
}
