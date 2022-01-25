package com.studies.okei.domain.authorization

import com.studies.okei.data.entities.UserAuthData
import io.ktor.client.statement.*

interface AuthenticationService {
    suspend fun authentication(userAuthData: UserAuthData): HttpResponse //: User
}