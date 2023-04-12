package com.plugi.domain.gateways.cacheGateway


val cacheGateway by lazy { CacheGateWay() }

class CacheGateWay : ICacheGateWay {
    private object Con{
        const val WORKTABLE = "A"
    }

    //    override fun saveHome(it: HomeModel) {
//        CacheHelper.save(Con.HOME,it)
//    }

}