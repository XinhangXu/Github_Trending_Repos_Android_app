package com.xinhangxu.githubtrending


object RestServiceBuilder {

    private var service: RestService? = null

    val apiService: RestService?
        get() {

            service = ServiceGenerator.createService<RestService>(RestService::class.java)

            return service
        }
}
