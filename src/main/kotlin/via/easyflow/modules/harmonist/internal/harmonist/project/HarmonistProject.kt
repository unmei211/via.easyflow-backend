package via.easyflow.modules.harmonist.internal.harmonist.project

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import via.easyflow.core.exception.NotFoundException
import via.easyflow.modules.project.api.contract.`in`.member.ConnectMembersIn
import via.easyflow.modules.project.api.model.ProjectMemberViaRolesModel
import via.easyflow.modules.project.api.interaction.service.member.IProjectMemberService
import via.easyflow.modules.project.api.interaction.service.project.IProjectService

@Service
class HarmonistProject(
    private val projectService: IProjectService,
    private val memberService: IProjectMemberService,
    // TODO: authService
) : IProjectHarmonist {

    override fun processConnectMembers(connectMembersIn: ConnectMembersIn): Flux<ProjectMemberViaRolesModel> {
        val existsMono = projectService.projectIsExists(connectMembersIn.projectId).flatMap {
            if (!it) {
                Mono.error {
                    NotFoundException("Not found project")
                }
            } else {
                Mono.just(it)
            }
        }

        val connectMemberFlux = existsMono.flatMapMany {
            memberService.connectMembers(connectMembersIn = connectMembersIn)
        }

        return connectMemberFlux
    }
}