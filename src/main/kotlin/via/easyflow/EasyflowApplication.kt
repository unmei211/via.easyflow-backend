package via.easyflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.modulith.Modulithic
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@Modulithic(
//    sharedModules = ["shared"],
//    additionalPackages = ["via.easyflow"],
)
// @EnableCaching
@EnableRetry
@EnableScheduling
@EnableWebFlux
@EnableWebFluxSecurity
@ConfigurationPropertiesScan
// @EnableAsync
class EasyflowApplication

fun main(args: Array<String>) {
    runApplication<EasyflowApplication>(*args)
}
