package forexbet.tradingforecasts.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;

import java.util.Map;

@Configuration
public class ApplicationBeanConfiguration {

//    private final CloudinaryConfig cloudinaryConfig;
//
//    public ApplicationBeanConfiguration(CloudinaryConfig cloudinaryConfig) {
//        this.cloudinaryConfig = cloudinaryConfig;
//    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public Cloudinary cloudinary() {
//        return new Cloudinary(
//                Map.of(
//                        "cloud_name", cloudinaryConfig.getCloudName(),
//                        "api_key", cloudinaryConfig.getApiKey(),
//                        "api_secret", cloudinaryConfig.getApiSecret()
//                )
//        );
//    }

}
