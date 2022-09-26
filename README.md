# tcp-file-transfer

# Tools

1. Java 17.0.3
2. Gradle 7.5

# How to Run

In the source code there are 2 files with main functions. In the source code there are 2 files with main functions. 
One for the client and one for the server.

For server in build.gradle.kts: 

`application { 
mainClass.set( "me.nikol.tcpfiletransfer.server.TcpFileTransferServerStarterKt")
}`


For client in build.gradle.kts:

`application { 
mainClass.set( "me.nikol.tcpfiletransfer.client.TcpFileTransferClientStarterKt")
}`

For all in terminal: `gradle run`