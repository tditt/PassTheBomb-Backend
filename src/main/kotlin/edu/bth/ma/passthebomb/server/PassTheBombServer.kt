package edu.bth.ma.passthebomb.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PassTheBombServer

fun main(args: Array<String>) {
	runApplication<PassTheBombServer>(*args)
}
