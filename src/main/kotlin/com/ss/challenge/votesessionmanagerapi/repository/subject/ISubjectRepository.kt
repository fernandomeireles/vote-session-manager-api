package com.ss.challenge.votesessionmanagerapi.repository.subject

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ISubjectRepository : JpaRepository<SubjectEntity, Long> {

    fun findByIsActiveOrderByIdAsc(status: Boolean = true): List<SubjectEntity>?
}
