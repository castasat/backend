import backend.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class FirstNameRoutesTest {
    @Test
    fun testGetFirstName() {
        withTestApplication({
            module(testing = true)
        }) {
            handleRequest(HttpMethod.Get, "/first_name")
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