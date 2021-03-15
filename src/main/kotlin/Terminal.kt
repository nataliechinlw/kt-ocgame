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

    fun getInput(isValid: (String) -> String?): String {
        while (true) {
            val input = getInput()
            val errorMessage = isValid(input) ?: return input
            printMessage("Bad input: $errorMessage")
        }
    }
}