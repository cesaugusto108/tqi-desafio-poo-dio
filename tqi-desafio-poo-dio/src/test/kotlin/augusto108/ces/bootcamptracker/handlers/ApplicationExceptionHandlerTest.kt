package augusto108.ces.bootcamptracker.handlers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
class ApplicationExceptionHandlerTest(@Autowired private val mockMvc: MockMvc) : TestContainersConfig() {

    @Test
    fun handleNotAcceptable() {
        mockMvc.perform(get("${API_VERSION}courses").accept(MediaType.APPLICATION_XML))
            .andExpect(status().isNotAcceptable)
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.message", `is`("No acceptable representation")))
            .andExpect(jsonPath("$.status", `is`("NOT_ACCEPTABLE")))
            .andExpect(jsonPath("$.statusCode", `is`(406)))
    }

    @Test
    fun handleNotFound() {
        mockMvc.perform(get("${API_VERSION}courses/{id}", 0))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.message", `is`("No result found. For query: Id: 0")))
            .andExpect(jsonPath("$.status", `is`("NOT_FOUND")))
            .andExpect(jsonPath("$.statusCode", `is`(404)))

        mockMvc.perform(get("/all${API_VERSION}courses/"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.message", `is`("No endpoint GET /all${API_VERSION}courses/.")))
            .andExpect(jsonPath("$.status", `is`("NOT_FOUND")))
            .andExpect(jsonPath("$.statusCode", `is`(404)))
    }

    @Test
    fun handleBadRequest() {
        mockMvc.perform(get("${API_VERSION}courses/{id}", "aaa"))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.message", `is`("For input string: \"aaa\"")))
            .andExpect(jsonPath("$.status", `is`("BAD_REQUEST")))
            .andExpect(jsonPath("$.statusCode", `is`(400)))
    }
}