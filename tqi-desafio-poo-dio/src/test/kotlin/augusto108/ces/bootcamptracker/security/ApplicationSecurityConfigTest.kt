package augusto108.ces.bootcamptracker.security

import augusto108.ces.bootcamptracker.entities.Developer
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@ActiveProfiles("securitytest")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "1234", roles = ["ADMIN"])
class ApplicationSecurityConfigTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {
    @Test
    fun testRequestsWithSecurityConfig() {
        mockMvc.perform(get("/developers"))
            .andExpect(status().isOk)

        mockMvc.perform(
            post("/developers").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Developer()))
        )
            .andExpect(status().isCreated)

        mockMvc.perform(
            delete("/developers/{id}", 1).with(csrf())
        )
            .andExpect(status().isNoContent)
    }
}
