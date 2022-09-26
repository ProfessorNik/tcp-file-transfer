package me.nikol.tcpfiletransfer

import java.io.InputStream

fun InputStream.readLine() : String {
    val stringBuilder = StringBuilder()
    var symbol = read().toChar()
    while(symbol != '\n'){
        stringBuilder.append(symbol)
        symbol = read().toChar()
    }

    return stringBuilder.toString()
}