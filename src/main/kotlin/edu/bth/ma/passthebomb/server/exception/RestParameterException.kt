package edu.bth.ma.passthebomb.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


class RestParameterException private constructor(status: HttpStatus, reason: String) :
    ResponseStatusException(status, reason) {
    constructor() : this(HttpStatus.BAD_REQUEST, "REST parameters not supplied or in wrong format!")
}
