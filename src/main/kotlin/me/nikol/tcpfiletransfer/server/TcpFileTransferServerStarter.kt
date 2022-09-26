package me.nikol.tcpfiletransfer.server

fun main(args: Array<String>) {
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
        println(e.message)
    }

    println("Server shut down, goodbye!")
}