package com.plugi.domain.gateways.cacheGateway



object CacheHelper {

    private val data: HashMap<Any, Any?> = hashMapOf()

    fun <T> save(key: Any, value: T) {
        data[key] = value
    }
    fun clear(key: Any){
        data[key] = null
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> load(key: Any): T? {
        val any = data[key]
        return if (any == null)
            null
        else
            any as T
//        return data[key].let {
//            it as T?
//        }
    }

}