package connection

import java.io.*
import java.net.Socket

class Connection(private val socket: Socket) {
    private val reader: BufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
    private val writer: BufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
    var alive = true;

    fun getMessage() : String?{
        if(reader.ready()){
            return reader.readLine()
        }
        return null
    }

    fun sendMessage(message: String){
        try{
            writer.write(message)
        }
        catch (e : IOException){
            e.printStackTrace()
        }
    }

    fun flush (){
        try {
            writer.flush()
        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }

    fun close() : Boolean{
        return try{
            reader.close()
            writer.close()
            socket.close()
            alive = false
            true
        } catch (e: IOException){
            e.printStackTrace()
            false
        }
    }
}