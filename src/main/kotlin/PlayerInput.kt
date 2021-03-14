import java.lang.Exception

class PlayerInput(override val input: String, isPredictor: Boolean) : Input() {
    init {
        if (isPredictor)
            verifyInputWithPrediction(input)
        else
            verifyInputWithoutPrediction(input)
    }

    override val numberOfOpenHands = Regex("O").findAll(input).count()
    override val prediction = if (isPredictor) {
        getPrediction(input)
    } else null

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

    companion object {
        fun create(input: String?, isPredictor: Boolean): PlayerInput {
            if (input == null)
                throw Exception()
            else
                return PlayerInput(input, isPredictor)
        }
    }
}