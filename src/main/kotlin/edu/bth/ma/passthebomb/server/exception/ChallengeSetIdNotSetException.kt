package edu.bth.ma.passthebomb.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


class ChallengeSetIdNotSetException private constructor(status: HttpStatus, reason: String) :
    ResponseStatusException(status, reason) {
    constructor() : this(HttpStatus.BAD_REQUEST, "ID for challenge set not supplied")
}
