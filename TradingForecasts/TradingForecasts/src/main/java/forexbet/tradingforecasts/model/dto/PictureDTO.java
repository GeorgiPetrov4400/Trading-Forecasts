package forexbet.tradingforecasts.model.dto;

public class PictureDTO {

    private String title;

    private String url;

    private String publicId;

    public String getTitle() {
        return title;
    }

    public PictureDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureDTO setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureDTO setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
