package me.nikol.tcpfiletransfer.client

import java.io.File
import java.io.FileInputStream

fun main(args: Array<String>) {
    println("client")
    val fileName : String
    val address: String
    val port : Int

    try {
        fileName = if (args[0].contains('\n'))
            throw IllegalArgumentException("Filename must be without \\n")
        else args[0]

        address = args[1]
        port = args[2].toInt()
    } catch (e: IndexOutOfBoundsException) {
        println("Cli args: fileName server_ip_or_dns_address server_port")
        return
    }

    try {
        TcpFileTransferClientSocket(address, port).use {
            transferFile(it, fileName)
        }
    } catch (e: Exception) {
        println("File transfer failed: ${e.message}")
    }
}

fun transferFile(client: TcpFileTransferClientSocket, fileName: String){
    FileInputStream(fileName).use {
        client.transferFile(fileName, File(fileName).length(), it)
    }

    println(client.receiveAboutTransferFile())
}