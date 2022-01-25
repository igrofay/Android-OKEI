package com.studies.okei.domain.content

import io.ktor.client.statement.*

interface ContentService {
    suspend fun calendarPlan(token: String): HttpResponse//: List<Month>
    suspend fun listTeacher(token: String, nameMonth: String): HttpResponse//: List<Teacher>
}