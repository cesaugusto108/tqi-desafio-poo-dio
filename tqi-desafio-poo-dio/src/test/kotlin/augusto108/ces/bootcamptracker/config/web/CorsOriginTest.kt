package augusto108.ces.bootcamptracker.config.web

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
class CorsOriginTest(@Autowired private val mockMvc: MockMvc) : TestContainersConfig() {

    private val urlTemplate = "${API_VERSION}courses"

    @Test
    fun allowedOriginTest() {
        mockMvc.perform(get(urlTemplate).header(HEADER_KEY, HEADER_VALUE)).andExpect(status().isOk)
    }

    @Test
    fun forbiddenOriginTest() {
        mockMvc.perform(get(urlTemplate).header(HEADER_KEY, HEADER_VALUE_FORBIDDEN)).andExpect(status().isForbidden)
    }
}