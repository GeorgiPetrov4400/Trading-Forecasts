package forexbet.tradingforecasts.model.entity;

import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String publicId;

    @ManyToOne
    private Forecast forecast;

    public Picture() {
    }

    public String getTitle() {
        return title;
    }

    public Picture setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Picture setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public Picture setPublicId(String publicId) {
        this.publicId = publicId;
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
