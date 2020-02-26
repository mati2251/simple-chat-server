package server

import java.io.*
import java.net.ServerSocket
import java.net.Socket

class Server(port: Int) {
    private val serverSocket = ServerSocket(port)

    init {
        val socket: Socket = serverSocket.accept()
        val inFromClient = BufferedReader(InputStreamReader(socket.getInputStream()))
        val outFromClient = DataOutputStream(socket.getOutputStream())
        println(inFromClient.readLine())
    }
}