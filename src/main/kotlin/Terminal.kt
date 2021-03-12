object Terminal {
    fun printMessage(message: String) = println(message)
    fun getInput(): String {
        while (true) {
            print("\t")
            val input = readLine()
            if (input != null)
                return input
        }

    }
}