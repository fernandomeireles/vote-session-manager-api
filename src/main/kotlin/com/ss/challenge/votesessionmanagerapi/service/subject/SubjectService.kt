package com.ss.challenge.votesessionmanagerapi.service.subject

import com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject.SubjectDto
import com.ss.challenge.votesessionmanagerapi.repository.subject.SubjectRepository
import com.ss.challenge.votesessionmanagerapi.service.subject.converter.SubjectConverter
import com.ss.challenge.votesessionmanagerapi.service.subject.exception.SubjectNotFoundException
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private var subjectRepository: SubjectRepository,
    private var subjectConverter: SubjectConverter
) : ISubjectService {

    override fun create(subject: String): SubjectDto {
        return subjectConverter.toDto(subjectRepository.create(subject))
    }

    override fun findSubjectActive(): List<SubjectDto>? {
        return subjectConverter.listToDto(
            subjectRepository.findByIsActiveOrderByIdAsc() ?: throw SubjectNotFoundException(0L)
        )
    }

    override fun find(idSubject: Long): SubjectDto {
        return subjectConverter.toDto(subjectRepository.find(idSubject))
    }

    override fun inactiveSubject(idSubject: Long): SubjectDto? {
        val subject = subjectRepository.find(idSubject)
        subject.isActive = false
        return subjectConverter.toDto(subjectRepository.update(subject))
    }
}
