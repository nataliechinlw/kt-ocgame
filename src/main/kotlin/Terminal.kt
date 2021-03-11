object Terminal {
    fun printMessage(message: String) = println(message)
    fun getInput(): String? {
        print("\t")
        return readLine()
    }
}