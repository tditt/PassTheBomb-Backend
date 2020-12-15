package edu.bth.ma.passthebomb.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ChallengeSetNotFoundException private constructor(status: HttpStatus, reason: String) :
    ResponseStatusException(status, reason) {
    constructor() : this(HttpStatus.BAD_REQUEST, "Challenge set was not found")
}
