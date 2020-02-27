package client

import connection.Connection

class Client(private val connection: Connection, private val clientLists: List<Client>) : Runnable {

    private lateinit var thread: Thread
    private var alive = false

    @Synchronized
    fun startSession() {
        if (alive) return
        alive = true
        thread = Thread(this)
        thread.start()
    }

    @Synchronized
    fun closeSession() {
        if (!alive) return
        alive = false
        try {
            connection.close()
            thread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun run() {
        while (connection.alive) {
            val reader = connection.getMessage()
            if (reader != null) {
                println(reader)
                TODO("Implements send only to people who need is")
                for(client in clientLists){
                    client.send(reader)
                }
            }
        }
    }

    fun send(msg: String) {
        connection.sendMessage(msg + "\n")
        connection.flush()
    }

    companion object {
        const val IDLE_TIME = 10;
    }
}