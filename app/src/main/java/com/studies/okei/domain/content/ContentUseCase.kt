package com.studies.okei.domain.content

import android.util.Log
import com.studies.okei.R
import com.studies.okei.app.toast
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentUseCase {
    suspend inline fun<reified T> processResponse(
        httpResponse: ()-> HttpResponse,
        crossinline success: (T)-> Unit,
        crossinline connectionError: (Int)->Unit,
        crossinline error401:()->Unit
    ){
        try {
            val response = httpResponse()
            withContext(Dispatchers.Main){
                when(response.status){
                    HttpStatusCode.OK -> success(response.receive())
                    HttpStatusCode.Unauthorized -> error401()
                    HttpStatusCode.InternalServerError -> connectionError(R.string.server_error)
                }
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
               Log.e("error", e.message.toString())
                connectionError(R.string.connection_error)
            }
        }
    }
}