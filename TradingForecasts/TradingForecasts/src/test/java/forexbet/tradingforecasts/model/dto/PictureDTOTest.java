package forexbet.tradingforecasts.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PictureDTOTest {

    @Test
    public void testSetTitle() {
        PictureDTO pictureDTO = new PictureDTO();
        String title = "Test title";
        pictureDTO.setTitle(title);
        Assertions.assertEquals(title, pictureDTO.getTitle());
    }

    @Test
    public void testSetUrl() {
        PictureDTO pictureDTO = new PictureDTO();
        String url = "https://example.com/test.jpg";
        pictureDTO.setUrl(url);
        Assertions.assertEquals(url, pictureDTO.getUrl());
    }

    @Test
    public void testSetPublicId() {
        PictureDTO pictureDTO = new PictureDTO();
        String publicId = "abc123";
        pictureDTO.setPublicId(publicId);
        Assertions.assertEquals(publicId, pictureDTO.getPublicId());
    }
}
