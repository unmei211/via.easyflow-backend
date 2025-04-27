package via.easyflow.reactive

import java.time.Duration
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ReactivityTest {
    @Test
    fun testDelayed() {
        Flux
            .just(1, 2, 3, 4)
            .concatMap { Mono.just(it).delayElement(Duration.ofMillis((5 - it.toLong()) * 1000)) }
            .map {
                println("processed: $it")
                it
            }
            .subscribe()

        Thread.sleep(20000)
    }
}