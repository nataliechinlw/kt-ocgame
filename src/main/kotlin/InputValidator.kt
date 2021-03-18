object InputValidator {
    fun yesNo(input: String) = "input should be either Y or N".takeIf { !Regex("[NY]").matches(input) }
    fun inputWithPrediction(input: String) =
        "correct input should be of the form CC3, where the first two letters indicate [O]pen or [C]losed state for each hand, followed by the prediction (0-4)".takeIf {
            !Regex("[OC][OC][0-4]").matches(input)
        }

    fun inputWithoutPrediction(input: String) =
        "correct input should be of the form CC, where the first two letters indicate [O]pen or [C]losed state for each hand".takeIf {
            !Regex("[OC][OC]").matches(input)
        }
    fun positiveInteger(input: String) = "input should be a positive integer".takeIf { !Regex("[1-9]").matches(input) }
}