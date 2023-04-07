package forexbet.tradingforecasts.model.dto;

import forexbet.tradingforecasts.model.entity.Category;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ForecastDTOTest {

    @Mock
    private Category category;

    @Test
    public void testGettersAndSetters() {
        ForecastDTO forecastDTO = new ForecastDTO()
                .setId(1L)
                .setCategory(category)
                .setForecastType(ForecastTypeEnum.Short)
                .setDescription("Test forecast")
                .setPictureUrl("https://example.com/picture.jpg")
                .setPrice(BigDecimal.valueOf(10.00))
                .setCreated(LocalDateTime.of(2023, 4, 7, 10, 30))
                .setClosed(LocalDateTime.of(2023, 4, 10, 12, 0))
                .setActive(true);

        assertEquals(1L, forecastDTO.getId());
        assertEquals(category, forecastDTO.getCategory());
        assertEquals(ForecastTypeEnum.Short, forecastDTO.getForecastType());
        assertEquals("Test forecast", forecastDTO.getDescription());
        assertEquals("https://example.com/picture.jpg", forecastDTO.getPictureUrl());
        assertEquals(BigDecimal.valueOf(10.00), forecastDTO.getPrice());
        assertEquals(LocalDateTime.of(2023, 4, 7, 10, 30), forecastDTO.getCreated());
        assertEquals(LocalDateTime.of(2023, 4, 10, 12, 0), forecastDTO.getClosed());
        assertEquals(true, forecastDTO.isActive());
    }
}
