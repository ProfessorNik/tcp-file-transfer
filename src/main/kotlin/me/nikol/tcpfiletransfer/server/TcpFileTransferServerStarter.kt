package me.nikol.tcpfiletransfer.server

import org.apache.logging.log4j.LogManager

fun main(args: Array<String>) {
    val logger = LogManager.getLogger("main")

    println("server")
    val port : Int
    try{
        port = args[0].toInt()
    } catch (e: IndexOutOfBoundsException) {
        println("Cli args: port")
        return
    }

    try {
        val server = TcpFileTransferServer(port)
        server.start()
    } catch (e: Exception) {
        logger.atError().withThrowable(e).log("Server error: ")
    }

    println("Server shut down, goodbye!")
}