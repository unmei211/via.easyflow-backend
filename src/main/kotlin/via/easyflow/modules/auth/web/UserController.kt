package via.easyflow.modules.auth.web

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import via.easyflow.core.layer.LayerType
import via.easyflow.core.layer.manager.HashLayerConverterManager
import via.easyflow.modules.auth.model.dto.UserModelDto
import via.easyflow.modules.auth.model.internal.UserModel

@Controller
class UserController(@Qualifier("authLayerConverter") private val layerManager: HashLayerConverterManager<LayerType>) :
    IUserController {
    private val dtoModelConverter = layerManager.getLayerConverter(LayerType.MODEL, LayerType.DTO)

    override fun getUserById(id: String): Mono<ResponseEntity<UserModelDto>> {
        val userModel = UserModel("aboba", "abobyan", "abibovich")

        val dto = dtoModelConverter.convert(userModel, UserModelDto::class)

        return Mono.just(ResponseEntity.ok(dto))
    }
}