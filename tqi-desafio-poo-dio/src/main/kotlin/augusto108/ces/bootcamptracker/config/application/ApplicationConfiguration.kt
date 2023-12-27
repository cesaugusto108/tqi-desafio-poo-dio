package augusto108.ces.bootcamptracker.config.application

import augusto108.ces.bootcamptracker.model.dto.BaseDto
import augusto108.ces.bootcamptracker.model.dto.PersonBaseDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.PagedResourcesAssembler

@Configuration
class ApplicationConfiguration {

    @Bean
    fun <T : BaseDto> getActivityPagedResourcesAssembler(): PagedResourcesAssembler<T> {
        return PagedResourcesAssembler(null, null)
    }

    @Bean
    fun <T : PersonBaseDto> getPersonPagedResourcesAssembler(): PagedResourcesAssembler<T> {
        return PagedResourcesAssembler(null, null)
    }
}