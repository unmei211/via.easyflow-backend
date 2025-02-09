package via.easyflow.core.layer.manager

import via.easyflow.core.layer.converter.HashLayerConverter
import via.easyflow.core.layer.converter.ILayerConverter
import via.easyflow.core.layer.converter.function.TypedConverterFunction
import via.easyflow.core.layer.factory.ILayerConverterFactory
import via.easyflow.core.tools.logger.logger

class HashLayerConverterManager<E : Enum<*>>(
    private val convertersMap: HashMap<Pair<E, E>, ILayerConverter<E, E>>,
) : IEnumerableLayerConverterManager<E> {
    private val log = logger()

    override fun getLayerConverter(layerFirst: E, layerSecond: E): ILayerConverter<E, E> {
        var converter = convertersMap[Pair(layerFirst, layerSecond)]

        if (converter == null) {
            converter = convertersMap[Pair(layerFirst, layerSecond)]
        }

        if (converter == null) {
            log.warn("Can't find converter for {}, {}", layerFirst, layerSecond)
            throw NoSuchElementException("Can't find converter for $layerFirst, $layerSecond")
        }

        return converter
    }

    override fun addLayerConverter(layerFirst: E, layerSecond: E, converter: ILayerConverter<E, E>) {
        convertersMap[layerFirst to layerSecond] = converter
        convertersMap[layerSecond to layerFirst] = converter
    }

    companion object {
        fun <E : Enum<*>> builder(converterFactory: ILayerConverterFactory<E, E>): LayerBuilder<E> {
            return LayerBuilder(converterFactory)
        }
    }

    class LayerBuilder<E : Enum<*>>(
        private val converterFactory: ILayerConverterFactory<E, E>,
    ) {
        private val converters = HashMap<Pair<E, E>, ILayerConverter<E, E>>()
        private val converterManager = HashLayerConverterManager(converters)

        private val fillers: MutableList<(builder: LayerBuilder<E>) -> Unit> = mutableListOf()

        fun fillers(vararg toAddFillers: (builder: LayerBuilder<E>) -> Unit): LayerBuilder<E> {
            this.fillers.addAll(toAddFillers)
            return this
        }

        fun filler(filler: (builder: LayerBuilder<E>) -> Unit): LayerBuilder<E> {
            this.fillers + filler
            return this
        }

        class TransitionBuilder<E : Enum<*>>(
            private val layerConverter: HashLayerConverter<E, E>,
            private val self: LayerBuilder<E>
        ) {
            fun transition(f: (converter: HashLayerConverter<E, E>) -> List<TypedConverterFunction<*, *>>): LayerBuilder<E> {
                f(layerConverter).forEach { layerConverter.addConverterFunction(it) }
                return self
            }
        }

        fun layer(
            layers: Pair<E, E>
        ): TransitionBuilder<E> {
            val layerConverter = converterFactory.createConverter(layers.first, layers.second)
            converters[layers] = layerConverter
            converters[layers.copy(first = layers.second, second = layers.first)] = layerConverter
            return TransitionBuilder(layerConverter as HashLayerConverter, this)
        }

        fun build(): HashLayerConverterManager<E> {
            fillers.forEach { it(this) }
            return converterManager
        }
    }
}