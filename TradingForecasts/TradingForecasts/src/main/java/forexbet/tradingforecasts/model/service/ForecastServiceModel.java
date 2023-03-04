package forexbet.tradingforecasts.model.service;

import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;

import java.math.BigDecimal;

public class ForecastServiceModel {

    private Long id;

    private String description;

    private String pictureUrl;

    private BigDecimal price;

    private CategoryNameEnum category;

    private ForecastTypeEnum forecastType;

    public ForecastServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public ForecastServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ForecastServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ForecastServiceModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ForecastServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CategoryNameEnum getCategory() {
        return category;
    }

    public ForecastServiceModel setCategory(CategoryNameEnum category) {
        this.category = category;
        return this;
    }

    public ForecastTypeEnum getForecastType() {
        return forecastType;
    }

    public ForecastServiceModel setForecastType(ForecastTypeEnum forecastType) {
        this.forecastType = forecastType;
        return this;
    }
}
