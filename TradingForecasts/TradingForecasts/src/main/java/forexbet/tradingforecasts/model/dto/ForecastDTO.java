package forexbet.tradingforecasts.model.dto;

import forexbet.tradingforecasts.model.entity.Category;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ForecastDTO {

    private Long id;

    private Category category;

    private ForecastTypeEnum forecastType;

    private String description;

    private String pictureUrl;

    private BigDecimal price;

    private LocalDateTime created;

    private LocalDateTime closed;

    private boolean isActive;

    public ForecastDTO() {
    }

    public Long getId() {
        return id;
    }

    public ForecastDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ForecastDTO setCategory(Category category) {
        this.category = category;
        return this;
    }

    public ForecastTypeEnum getForecastType() {
        return forecastType;
    }

    public ForecastDTO setForecastType(ForecastTypeEnum forecastType) {
        this.forecastType = forecastType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ForecastDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ForecastDTO setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ForecastDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ForecastDTO setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getClosed() {
        return closed;
    }

    public ForecastDTO setClosed(LocalDateTime closed) {
        this.closed = closed;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ForecastDTO setActive(boolean active) {
        isActive = active;
        return this;
    }
}
