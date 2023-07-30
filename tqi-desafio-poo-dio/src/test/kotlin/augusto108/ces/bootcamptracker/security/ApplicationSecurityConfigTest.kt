package augusto108.ces.bootcamptracker.security

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.entities.Developer
import augusto108.ces.bootcamptracker.util.API_VERSION
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("securitytest")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "1234", roles = ["ADMIN"])
class ApplicationSecurityConfigTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) : TestContainersConfig() {
    @Test
    fun testRequestsWithSecurityConfig() {
        mockMvc.perform(get("${API_VERSION}developers"))
            .andExpect(status().isOk)

        mockMvc.perform(
            post("${API_VERSION}developers").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Developer()))
        )
            .andExpect(status().isCreated)

        mockMvc.perform(
            delete("${API_VERSION}developers/{id}", 1).with(csrf())
        )
            .andExpect(status().isNoContent)
    }
}
