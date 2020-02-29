import java.io.*
import java.net.Socket


fun main(args: Array<String>) {
    val host: String = "localhost"
    val port: Int = 8000
    try {
        Socket(host, port).use { socket ->
            BufferedReader(InputStreamReader(socket.getInputStream())).use { `in` ->
                BufferedWriter(OutputStreamWriter(socket.getOutputStream())).use { out ->
                    BufferedReader(InputStreamReader(System.`in`)).use { stdIn ->
                        val input = Thread(Runnable {
                            var msg: String?
                            try {
                                while (`in`.readLine().also { msg = it } != null) {
                                    println(msg)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        })
                        input.start()
                        val userName = "User" + (Math.random() * 200).toInt()
                        var msg: String
                        try {
                            while (stdIn.readLine().also { msg = it } != null) {
                                for (i in 0 until msg.length) print("\b")
                                out.write("$userName: $msg\n")
                                out.flush()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}