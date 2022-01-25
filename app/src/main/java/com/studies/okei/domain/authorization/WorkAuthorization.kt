package com.studies.okei.domain.authorization

import android.content.Context
import androidx.work.*
import com.studies.okei.data.datasourse.web.Web.authentication
import com.studies.okei.data.entities.UserAuthData
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

//TODO Создать фабрику для Worker
//TODO Добавить обработку доп. ошибок
class WorkAuthorization(appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params)  {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        val data = inputData.getString(KEY_INPUT_DATA)?.let {
            Json.decodeFromString<UserAuthData>(it)
        } ?: return@withContext Result.failure()
        val response = authentication(data)
        when(response.status){
            OK ->return@withContext Result.success(
                workDataOf(
                    KEY_OUT_DATA to response.receive() as String
                )
            )
            Unauthorized-> return@withContext Result.failure()
            InternalServerError-> return@withContext Result.retry()
            else -> Result.retry()
        }
    }

    companion object{
        private const val KEY_INPUT_DATA ="KEY_INPUT_DATA"
        const val KEY_OUT_DATA = "KEY_OUT_DATA"
        fun newInstance(userAuthData: String) : OneTimeWorkRequest {
            val data = workDataOf(KEY_INPUT_DATA to userAuthData)
            val constraint  = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            return OneTimeWorkRequestBuilder<WorkAuthorization>()
                .setConstraints(constraint)
                .setInputData(data)
                .build()
        }
    }
}