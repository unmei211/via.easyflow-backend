package via.easyflow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.Modulithic
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@Modulithic(
//    sharedModules = ["shared"],
//    additionalPackages = ["via.easyflow"],
)
// @EnableCaching
@EnableRetry
@EnableScheduling
// @EnableAsync
class EasyflowApplication

fun main(args: Array<String>) {
    runApplication<EasyflowApplication>(*args)
}
