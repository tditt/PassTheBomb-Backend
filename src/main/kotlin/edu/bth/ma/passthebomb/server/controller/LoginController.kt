package edu.bth.ma.passthebomb.server.controller

import edu.bth.ma.passthebomb.server.domain.User
import edu.bth.ma.passthebomb.server.service.UserService
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

//unused currently

@RestController
@CrossOrigin
@RequestMapping(path = ["/api/login"])
class LoginController constructor(private val userService: UserService) {
    @RequestMapping(value = ["/authenticate"])
    fun authenticate(): Boolean {
        val auth = SecurityContextHolder.getContext().authentication
        return auth != null && auth.isAuthenticated && auth !is AnonymousAuthenticationToken
    }

    @RequestMapping(value = ["/user"])
    fun getLoggedInUser(principal: Principal?): User? {
        return userService.getUserByName(principal?.getName())
    }
}