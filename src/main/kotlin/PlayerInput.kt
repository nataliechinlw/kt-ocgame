import java.lang.Exception

class PlayerInput(input: String, isPredictor: Boolean) {
    init {
        if (isPredictor)
            verifyInputWithPrediction(input)
        else
            verifyInputWithoutPrediction(input)
    }
    val numberOfOpenHands = countOpenHands(input)
    val prediction = if (isPredictor) { getPrediction(input) } else 0

    fun countOpenHands(input: String): Int {
        val openHandPattern = """O""".toRegex()
        return openHandPattern.findAll(input).count()
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