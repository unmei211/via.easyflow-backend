package via.easyflow.core.layer.manager

import via.easyflow.core.layer.converter.ILayerConverter

interface IEnumerableLayerConverterManager<E : Enum<*>> {
    fun getLayerConverter(layerFirst: E, layerSecond: E): ILayerConverter<E, E>
    fun getLayerConverter(layerMapping: Pair<E, E>): ILayerConverter<E, E> =
        this.getLayerConverter(layerMapping.first, layerMapping.second)

    fun addLayerConverter(layerFirst: E, layerSecond: E, converter: ILayerConverter<E, E>)
}