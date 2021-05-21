import backend.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class CustomerRoutesTest {
    @Test
    fun testGetCustomer() {
        withTestApplication({
            module(testing = true)
        }) {
            handleRequest(HttpMethod.Get, "/customer")
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