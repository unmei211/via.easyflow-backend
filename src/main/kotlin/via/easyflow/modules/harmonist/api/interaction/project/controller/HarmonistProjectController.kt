package via.easyflow.modules.harmonist.api.interaction.project.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import via.easyflow.core.tools.logger.logger
import via.easyflow.modules.harmonist.api.interaction.project.model.out.ConnectMembersOut
import via.easyflow.modules.harmonist.api.interaction.project.model.out.UpsertedProjectOut
import via.easyflow.modules.harmonist.internal.harmonist.project.IProjectHarmonist
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn

@Controller
class HarmonistProjectController(
    private val harmonistProjectService: IProjectHarmonist,
) : IHarmonistProjectController {
    private val log = logger()

    override fun processConnectMembers(requestBody: ConnectMembersViaRolesIn): Mono<ResponseEntity<ConnectMembersOut>> {
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

    override fun processCreateProject(requestBody: UpsertProjectIn): Mono<ResponseEntity<UpsertedProjectOut>> {
        log.info("processCreateProject request body: $requestBody")

        return harmonistProjectService
            .processCreateProject(requestBody)
            .map { project ->
                ResponseEntity.ok(UpsertedProjectOut(project))
            }
    }
}