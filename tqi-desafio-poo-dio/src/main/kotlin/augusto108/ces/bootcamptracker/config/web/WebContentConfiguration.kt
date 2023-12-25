package augusto108.ces.bootcamptracker.config.web

import augusto108.ces.bootcamptracker.converter.YamlMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import augusto108.ces.bootcamptracker.util.MediaType as UtilMediaType

@Configuration
@PropertySource("classpath:app_cors.properties")
class WebContentConfiguration : WebMvcConfigurer {

    @Value("\${cors.origins}")
    private val origins: String = ""

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer
            .favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("yml", MediaType.valueOf(UtilMediaType.APPLICATION_YAML))
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(YamlMessageConverter())
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        val allowedOrigins: Array<String> = origins.split(",").toTypedArray()

        registry
            .addMapping("/**")
            .allowedOrigins(*allowedOrigins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
            .allowCredentials(true)
    }
}