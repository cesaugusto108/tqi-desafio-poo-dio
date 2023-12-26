package augusto108.ces.bootcamptracker.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MediaTypeTest {

    @Test
    fun checkStringValue() {
        val mediaType = MediaType
        assertEquals("application/json", mediaType.JSON)
        assertEquals("application/x-yaml", mediaType.YAML)
    }
}