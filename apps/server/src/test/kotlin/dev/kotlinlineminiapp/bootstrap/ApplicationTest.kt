package dev.kotlinlineminiapp.bootstrap

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTest {
    @LocalServerPort
    private var port: Int = 0

    @Test
    fun `server starts without business endpoints`() {
        HttpClient.newHttpClient().use { client ->
            val request = HttpRequest.newBuilder(URI("http://localhost:$port/")).GET().build()
            val response = client.send(request, HttpResponse.BodyHandlers.discarding())

            assertEquals(404, response.statusCode())
        }
    }
}
