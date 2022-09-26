package me.nikol.tcpfiletransfer.client

import me.nikol.tcpfiletransfer.readLine
import java.io.InputStream
import java.net.Socket

class TcpFileTransferClientSocket(address: String, port: Int) : AutoCloseable {
    private val socket: Socket

    init {
        socket = Socket(address, port)
    }

    fun transferFile(fileName: String, fileSize: Long, fileInputStream: InputStream) {
        socket.getOutputStream().write("$fileName\n".toByteArray())
        socket.getOutputStream().write("$fileSize\n".toByteArray())
        fileInputStream.transferTo(socket.getOutputStream())
        socket.shutdownOutput()
    }

    fun receiveAboutTransferFile() : String{
        return socket.getInputStream().readLine()
    }

    override fun close() {
        socket.close()
    }
}