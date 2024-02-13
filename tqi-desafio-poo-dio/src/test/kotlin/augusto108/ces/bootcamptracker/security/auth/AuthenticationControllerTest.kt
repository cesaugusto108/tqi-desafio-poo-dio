package augusto108.ces.bootcamptracker.security.auth

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.security.model.AuthenticationModel
import augusto108.ces.bootcamptracker.security.model.Token
import augusto108.ces.bootcamptracker.util.API_VERSION
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
class AuthenticationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) : TestContainersConfig() {

    @Value("\${security.test.user.password}")
    private val password: String = ""

    @Test
    fun authenticate() {
        val authenticationModel = AuthenticationModel("csim", password)

        val authenticationResult: MvcResult = mockMvc.perform(
            post("${API_VERSION}auth/login/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationModel))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username", `is`("csim")))
            .andExpect(jsonPath("$.authenticated", `is`(true))).andReturn()

        val token: String =
            objectMapper.readValue(authenticationResult.response.contentAsString, Token::class.java).token

        mockMvc.perform(
            get("${API_VERSION}instructors/{id}", "e8fd1a04-1c85-45e0-8f35-8ee8520e1800")
                .header("Authorization", "Bearer $token")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id", `is`("e8fd1a04-1c85-45e0-8f35-8ee8520e1800")))
            .andExpect(jsonPath("$.age", `is`(32)))
            .andExpect(jsonPath("$.name.lastName", `is`("Santos")))
            .andExpect(jsonPath("$.email", `is`("florinda@email.com")))
            .andExpect(
                jsonPath(
                    "$._links.self.href",
                    `is`("http://localhost${API_VERSION}instructors/e8fd1a04-1c85-45e0-8f35-8ee8520e1800")
                )
            )
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}instructors")))
    }

    @Test
    fun failedAuthenticationTest() {
        val authenticationModel = AuthenticationModel("abcd", "")

        val usernameNotFoundAuthenticationResult: MvcResult = mockMvc.perform(
            post("${API_VERSION}auth/login/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationModel))
        )
            .andExpect(status().isUnauthorized).andReturn()

        assertEquals(
            usernameNotFoundAuthenticationResult.response.contentAsString,
            "Authentication is necessary to access this endpoint"
        )
    }

    @Test
    fun noAuthenticationRequestTest() {
        val noAuthenticationResult: MvcResult = mockMvc.perform(
            get("${API_VERSION}instructors")
        )
            .andExpect(status().isUnauthorized).andReturn()

        assertEquals(
            noAuthenticationResult.response.contentAsString,
            "Authentication is necessary to access this endpoint"
        )
    }

    @Test
    fun wrongTokenFormatResquestTest() {
        mockMvc.perform(
            get("${API_VERSION}instructors")
                .header("Authorization", "Bearer anqçth;abç~qbhtçdam;qmaa")
        )
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.message", `is`("JWT strings must contain exactly 2 period characters. Found: 0 ")))
    }
}