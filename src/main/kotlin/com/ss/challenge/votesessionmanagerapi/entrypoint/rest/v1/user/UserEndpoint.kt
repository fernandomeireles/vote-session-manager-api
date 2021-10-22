package com.ss.challenge.votesessionmanagerapi.entrypoint.rest.v1.user

import com.ss.challenge.votesessionmanagerapi.service.user.IUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["vote-session-manager"])
@RestController
@Validated
@RequestMapping("api/v1/user")
class UserEndpoint(private val iUserService: IUserService) {

    @ApiOperation(value = "Creates a user for the selected in vote session")
    @PostMapping("/create")
    fun create(): UserDto {
        return iUserService.create()
    }

    @ApiOperation(value = "Get a user by Id")
    @GetMapping("{idUser}")
    fun getUser(@NotNull @PathVariable idUser: Long): UserDto? {
        return iUserService.findById(idUser)
    }

    @ApiOperation(value = "Get all users")
    @GetMapping("/findAll")
    fun getAllUser(): List<UserDto>? {
        return iUserService.findAll()
    }

    @ApiOperation(value = "Inactive user ID")
    @PutMapping("/inactiveUser/{idUser}")
    fun putInactiveUser(@NotNull @PathVariable idUser: Long): UserDto? {
        return iUserService.inactiveUser(idUser)
    }
}
