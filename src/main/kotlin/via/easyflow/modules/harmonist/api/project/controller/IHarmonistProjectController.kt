package via.easyflow.modules.harmonist.api.project.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.modules.harmonist.api.project.model.out.ConnectMembersOut
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn

@RestController
@RequestMapping("/front/project")
interface IHarmonistProjectController {

    @PostMapping("/{projectId}/connect-members")
    fun processConnectMembers(@RequestBody requestBody: ConnectMembersIn): Mono<ResponseEntity<ConnectMembersOut>>
}