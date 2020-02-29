package ConnectionListener

import connection.Connection
import server.Server
import java.io.IOException
import java.net.ServerSocket


class ConnectionListener(private val server: Server) : Runnable {

    private var socket: ServerSocket = Server.getSocket()
    var isRunning = false
        private set
    private lateinit var thread: Thread

    @Synchronized
    fun start() {
        if (isRunning) return
        isRunning = true
        thread = Thread(this)
        thread.start()
    }

    @Synchronized
    fun stop() {
        if (!isRunning) return
        print("Stop connection on :" + socket.localSocketAddress + "...")
        isRunning = false
        try {
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        println("TERMINATED!")
    }

    override fun run() {

        println("Listening for connections on: " + socket.localSocketAddress)

        try {
            while (isRunning) {
                val request = socket.accept()
                val connection = Connection(request)
                server.addConnection(connection)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}