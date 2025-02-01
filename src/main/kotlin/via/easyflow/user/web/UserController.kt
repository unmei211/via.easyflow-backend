package via.easyflow.user.web

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import via.easyflow.shared.layer.LayerType
import via.easyflow.shared.layer.manager.HashLayerConverterManager
import via.easyflow.user.model.dto.UserModelDto
import via.easyflow.user.model.internal.UserModel

class UserController(private val layerManager: HashLayerConverterManager<LayerType>) : IUserController {
    private val converter = layerManager.getLayerConverter(LayerType.MODEL, LayerType.DTO)
    override fun getUserById(id: String): Mono<ResponseEntity<UserModelDto>> {
        val userModel = UserModel("aboba", "abobyan", "abibovich")

        val dto = converter.convert(userModel, UserModelDto::class)

        return Mono.just(ResponseEntity.ok(dto))
    }
}