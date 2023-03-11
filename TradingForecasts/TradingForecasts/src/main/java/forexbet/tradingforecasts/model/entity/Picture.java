package forexbet.tradingforecasts.model.entity;

import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Column(nullable = false)
    private String pictureUrl;

    @ManyToOne
    private Forecast forecast;

    public Picture() {
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Picture setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public Picture setForecast(Forecast forecast) {
        this.forecast = forecast;
        return this;
    }
}
