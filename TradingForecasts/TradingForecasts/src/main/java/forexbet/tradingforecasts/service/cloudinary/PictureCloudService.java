package forexbet.tradingforecasts.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import forexbet.tradingforecasts.config.CloudinaryConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Service
public class PictureCloudService {

    private final Cloudinary cloudinary;

    public PictureCloudService(CloudinaryConfig cloudinaryConfig) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryConfig.getCloudName(),
                "api_key", cloudinaryConfig.getApiKey(),
                "api_secret", cloudinaryConfig.getApiSecret(),
                "secure", true));
    }

    public String savePicture(MultipartFile multipartFile) {
        String pictureId = UUID.randomUUID().toString();

        Map params = ObjectUtils.asMap(
                "public_id", pictureId,
                "overwrite", true,
                "resource_type", "image"
        );

        File tempFile = new File(pictureId);
        try {
            Files.write(tempFile.toPath(), multipartFile.getBytes());
            cloudinary.uploader().upload(tempFile, params);
            Files.delete(tempFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.format("https://res.cloudinary.com/dtg97g3ym/image/upload/v1678739665/" + pictureId + "." +
                getFileExtension(multipartFile.getOriginalFilename()));
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
