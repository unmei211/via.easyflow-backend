package via.easyflow.reactive

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

class ReactiveTest {
    @Test
    fun simpleFluxTest() {
        val numberFlux = Flux
            .fromIterable(1..5)
            .doOnSubscribe {
                println("На flux подписались")
            }
            .delayElements(Duration.ofMillis(400))
            .doOnNext { println(it) }
        val checker = Flux.from(numberFlux).doOnNext { println("Curent $it") }.subscribe()
        Flux.from(numberFlux).subscribe()
//        val countSubcsriber = numberFlux.count().doOnNext { println("next") }.thenMany(numberFlux)

//        countSubcsriber.subscribe()

        Thread.sleep(5000)
    }
}