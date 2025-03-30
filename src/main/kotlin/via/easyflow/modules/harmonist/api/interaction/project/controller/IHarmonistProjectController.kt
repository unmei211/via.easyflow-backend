package via.easyflow.modules.harmonist.api.interaction.project.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.modules.harmonist.api.interaction.project.model.out.ConnectMembersOut
import via.easyflow.modules.harmonist.api.interaction.project.model.out.UpsertedProjectOut
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersViaRolesIn
import via.easyflow.modules.project.api.contract.`in`.project.UpsertProjectIn

@RestController
@RequestMapping("/front/project")
interface IHarmonistProjectController {
    @PostMapping("/{projectId}/connect-members")
    fun processConnectMembers(@RequestBody requestBody: ConnectMembersViaRolesIn):
            Mono<ResponseEntity<ConnectMembersOut>>

    @PostMapping("/create")
    fun processCreateProject(@RequestBody requestBody: UpsertProjectIn):
            Mono<ResponseEntity<UpsertedProjectOut>>
}