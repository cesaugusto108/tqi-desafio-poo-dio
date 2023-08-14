package augusto108.ces.bootcamptracker.converter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import org.springframework.http.MediaType
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import augusto108.ces.bootcamptracker.util.MediaType as UtilMediaType

class YamlMessageConverter : AbstractJackson2HttpMessageConverter(
    YAMLMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL),
    MediaType.parseMediaType(UtilMediaType.APPLICATION_YAML)
)