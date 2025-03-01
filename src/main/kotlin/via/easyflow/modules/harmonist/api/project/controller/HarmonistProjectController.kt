package via.easyflow.modules.harmonist.api.project.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.harmonist.api.project.model.out.ConnectMembersOut
import via.easyflow.modules.harmonist.internal.harmonist.project.IProjectHarmonist
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn

@Controller
class HarmonistProjectController(
    private val harmonistProjectService: IProjectHarmonist,
) : IHarmonistProjectController {
    private val log = logger()

    override fun processConnectMembers(requestBody: ConnectMembersIn): Mono<ResponseEntity<ConnectMembersOut>> {
        log.info("processConnectMembers request body: $requestBody")

        val processedProjectMemberFlux = harmonistProjectService
            .processConnectMembers(requestBody)
            .doOnNext {
                log.debug("Get processed connected member: {}", it)
            }

        return processedProjectMemberFlux
            .collectList()
            .flatMap {
                Mono.just(
                    ResponseEntity.ok(
                        ConnectMembersOut(
                            it
                        )
                    )
                )
            }
    }
}