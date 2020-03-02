package client

import connection.Connection

class Client(private val connection: Connection, private val clientLists: List<Client>) : Runnable {

    private lateinit var thread: Thread
    private var alive = false
    private lateinit var nick: String

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
            val reader: String? = connection.getMessage()
            if (reader != null) {
                val type = reader.split("?")
                if (type[0] == "nick") {
                    nick = type[1]
                    for (client in clientLists) {
                        client.send("WELCOME USER: $nick")
                    }
                } else if(type[0] == "msg" && nick.isNotEmpty()){
                    val props = type[1].split("&")
                    val propsMap = mutableMapOf<String, String>()
                    propsMap["text"] = ""
                    props.forEach{ propsMap[it.split("=")[0]] = it.split("=")[1]}
                    if(propsMap["to"] == "all" || propsMap["to"].isNullOrEmpty()) {
                        for (client in clientLists) {
                            client.send("$nick: ${propsMap["text"]}")
                        }
                    }
                    else{
                        val toUsers: List<String> = propsMap["to"]!!.split(",")
                        for (client in clientLists) {
                            if(toUsers.contains(client.nick)){
                                client.send("$nick: ${propsMap["text"]}")
                                this.send("$nick: ${propsMap["text"]}")
                            }
                        }
                    }
                }
                else if(nick.isEmpty()){
                 this.send("You don't have nick. You must assign it by command \"nick?yournickname\"")
                }
                else{
                    this.send("Bad request")
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