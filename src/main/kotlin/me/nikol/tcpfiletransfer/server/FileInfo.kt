package me.nikol.tcpfiletransfer.server

import org.apache.commons.io.FilenameUtils

data class FileInfo(val fileName: String, val size: Long) {
    val fileNameWithFullPath = "uploads\\${fileName}"
    val fullPath =  FilenameUtils.getPath(fileNameWithFullPath)!!
}