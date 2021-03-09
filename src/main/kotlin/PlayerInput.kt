import java.lang.Exception

class PlayerInput(input: String) {
    init {
        verifyInputWithoutPrediction(input)
    }
    val numberOfOpenHands = countHands(input)

    fun countHands(input: String): Int {
        val numberInFirstHand = if (input[0] == 'O') 1 else 0
        val numberInSecondHand = if (input[1] == 'O') 1 else 0
        return numberInFirstHand + numberInSecondHand
    }

    fun getPrediction(input: String): Int {
        return input[2].toString().toInt()
    }

    fun verifyInputWithPrediction(input: String) {
        val validInputPattern = """[OC][OC][0-4]""".toRegex()
        if (!validInputPattern.matches(input))
            throw Exception("Bad input: correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand, followed by the prediction (0-4).")
    }

    fun verifyInputWithoutPrediction(input: String) {
        val validInputPattern = """[OC][OC]""".toRegex()
        if (!validInputPattern.matches(input))
            throw Exception("Bad input: correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand.")
    }


}