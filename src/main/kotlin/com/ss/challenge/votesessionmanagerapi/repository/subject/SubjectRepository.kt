package com.ss.challenge.votesessionmanagerapi.repository.subject

import com.ss.challenge.votesessionmanagerapi.core.usercase.subject.SubjectEntity
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SubjectRepository(private var repository: ISubjectRepository) {

    fun create(subject: String): SubjectEntity {
        return repository.save(
            SubjectEntity(
                null,
                subject,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
            )
        )
    }

    fun find(idSubject: Long): SubjectEntity {
        return repository.findById(idSubject).orElseThrow {
            throw SubjectNotFoundException(idSubject)
        }
    }

    fun update(subjectEntity: SubjectEntity): SubjectEntity {
        return repository.save(subjectEntity)
    }

    fun findByIsActiveOrderByIdAsc(): List<SubjectEntity>? {
        return repository.findByIsActiveOrderByIdAsc(true)
    }
}
