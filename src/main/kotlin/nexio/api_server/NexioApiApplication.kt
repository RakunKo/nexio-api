package nexio.api_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NexioApiApplication

fun main(args: Array<String>) {
    runApplication<NexioApiApplication>(*args)
}
