package forexbet.tradingforecasts.model.entity;

import forexbet.tradingforecasts.model.entity.enums.ForecastTypeEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture_url", nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column
    private LocalDateTime closed;

    @Column(name = "forecast_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ForecastTypeEnum forecastType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    public Forecast() {
        this.created = LocalDateTime.now();
        this.closed = null;
        this.isActive = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getClosed() {
        return closed;
    }

    public void setClosed(LocalDateTime closed) {
        this.closed = closed;
    }

    public ForecastTypeEnum getForecastType() {
        return forecastType;
    }

    public void setForecastType(ForecastTypeEnum forecastType) {
        this.forecastType = forecastType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
