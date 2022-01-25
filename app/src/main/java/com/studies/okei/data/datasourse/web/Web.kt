package com.studies.okei.data.datasourse.web

import com.studies.okei.data.entities.UserAuthData
import com.studies.okei.domain.authorization.AuthenticationService
import com.studies.okei.domain.content.ContentService
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.cache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object Web : AuthenticationService, ContentService {
    private const val Url_Server = "http://176.28.64.201:3434"
    // 192.168.10.33:3434
    // 176.28.64.201:3434
    private val client by lazy {
        HttpClient{
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000
                connectTimeoutMillis = 10_000
            }
            install(HttpCache)
        }
    }

    override suspend fun authentication(userAuthData: UserAuthData): HttpResponse{
        return client.post("$Url_Server/login") {
            contentType(ContentType.Application.Json)
            body = userAuthData
        }
    }

    override suspend fun calendarPlan(token: String): HttpResponse {
        return client.get("$Url_Server/months"){
            headers{
                append(HttpHeaders.Authorization, token)
            }
        }
    }

    override suspend fun listTeacher(token: String, nameMonth: String): HttpResponse {
        return client.get("$Url_Server/months/$nameMonth"){
            headers{
                append(HttpHeaders.Authorization, token)
            }
        }
    }


}