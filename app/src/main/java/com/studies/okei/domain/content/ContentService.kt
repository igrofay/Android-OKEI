package com.studies.okei.domain.content

import com.studies.okei.data.entities.VoteCriterion
import io.ktor.client.statement.*

interface ContentService {
    suspend fun calendarPlan(token: String): HttpResponse//: List<Month>
    suspend fun listTeacher(token: String, nameMonth: String): HttpResponse//: List<Teacher>
    suspend fun listVoteCriterion(token: String, nameMonth: String, loginTeacher: String): HttpResponse
    suspend fun putChangeVoteCriterion(token: String, nameMonth: String, loginTeacher: String, voteCriterion: VoteCriterion): HttpResponse
}