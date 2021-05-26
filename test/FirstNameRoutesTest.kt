import backend.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalSerializationApi
class FirstNameRoutesTest {
    @Test
    fun testGetFirstName() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "http://localhost/first_name")
                .apply {
                    assertEquals(
                        expected = HttpStatusCode.NotFound,
                        actual = response.status(),
                        message = "Response status should be NotFound"
                    )
                }
        }
    }
}