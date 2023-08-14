package augusto108.ces.bootcamptracker.config.docs

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfiguration {
    @Bean
    fun openApiConfig(): OpenAPI =
        OpenAPI().info(Info().title("Bootcamp tracker").description("Kotlin Backend Developer challenge").version("v1"))
}