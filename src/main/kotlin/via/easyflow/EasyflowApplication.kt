package via.easyflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@EnableWebFluxSecurity
@ConfigurationPropertiesScan
class EasyflowApplication

fun main(args: Array<String>) {
    runApplication<EasyflowApplication>(*args)
}
