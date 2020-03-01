package server

import client.Client
import connection.Connection
import connectionListener.ConnectionListener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket

class Server(port: Int) {

    var socket: ServerSocket = ServerSocket(port)
        private set
    private var connectionListener: ConnectionListener = ConnectionListener(this)
    private var userList = mutableListOf<Client>()

    fun start() {
        connectionListener.start()
        val stdIn = BufferedReader(InputStreamReader(System.`in`))
        val input: String? = stdIn.readLine()
        while ((input != null) && connectionListener.isRunning){
            if(input == "close"){
                break
            } else{
                for (c in userList) {
                    c.send("Admin: $input")
                }
            }
        }
        stop()
    }

    private fun stop() {
        connectionListener.stop()
        socket.close()
        for (c in userList) {
            c.closeSession()
        }
        println("Server terminated!")
    }


    @Synchronized
    fun addConnection(connection: Connection) {
        val c = Client(connection, userList)
        userList.add(c)
        c.startSession()
    }
}