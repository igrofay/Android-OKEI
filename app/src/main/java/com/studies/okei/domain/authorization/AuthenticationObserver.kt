package com.studies.okei.domain.authorization

import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.studies.okei.data.entities.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AuthenticationObserver(
    val success: (User)->Unit,
    val error: ()-> Unit
) : Observer<WorkInfo>{

    override fun onChanged(t: WorkInfo?) {
        t ?: return
        when(t.state){
            WorkInfo.State.FAILED-> error()
            WorkInfo.State.SUCCEEDED-> {
                val data = t.outputData.getString(WorkAuthorization.KEY_OUT_DATA)!!
                success(
                    Json.decodeFromString(data)
                )
            }
        }
    }
}