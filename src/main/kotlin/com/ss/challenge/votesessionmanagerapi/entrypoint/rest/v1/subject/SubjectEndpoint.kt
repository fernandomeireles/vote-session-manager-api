package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.subject

import com.ss.challenge.votesessionmanagerapi.service.subject.ISubjectService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["vote-session-manager"])
@RestController
@Validated
@RequestMapping("api/v1/subject")
class SubjectEndpoint(private var iSubjectService: ISubjectService) {

    @ApiOperation(value = "Creates a subject for the selected in vote session")
    @PostMapping("/create")
    fun create(
        @NotNull
        @RequestHeader subject: String
    ): SubjectDto {
        return iSubjectService.create(subject)
    }

    @ApiOperation(value = "Get a subject by Id")
    @GetMapping("{idSubject}")
    fun getSubject(@NotNull @PathVariable idSubject: Long): SubjectDto? {
        return iSubjectService.find(idSubject)
    }

    @ApiOperation(value = "Get all subjects actives")
    @GetMapping("/getActive")
    fun getActiveSubjects(): List<SubjectDto>? {
        return iSubjectService.findSubjectActive()
    }

    @ApiOperation(value = "Get all subjects actives")
    @PutMapping("/inactiveSubject/{idSubject}")
    fun putInactiveSubject(@NotNull @PathVariable idSubject: Long): SubjectDto? {
        return iSubjectService.inactiveSubject(idSubject)
    }
}
