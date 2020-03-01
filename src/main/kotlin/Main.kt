import server.Server
import java.lang.NumberFormatException

fun main(args: Array<String>) {
    var port: Int = 1025;
    if (args.any { it == "--help" }) {
        displayHelp()
    } else {
        for (i in args.indices) {
            if (args[i] == "-p") {
                try {
                    port = args[i + 1].toInt()
                    println(port)
                    if (port !in 1..65535) {
                        displayHelp()
                    }
                } catch (e: NumberFormatException) {
                    println("BAD PORT")
                    displayHelp()
                }
            }
        }
    }
    if (port in 1..65535) {
        val server = Server(port)
        server.start()
    }
}

fun displayHelp() {
    println("That is simple server chat.")
    println("Arguments to custom server:")
    println("-p       to change port number")
    println("--help   to display help")
}